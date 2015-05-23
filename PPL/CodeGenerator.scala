/*
*	CodeGenerator
*/

import scala.collection.mutable.StringBuilder
import scala.collection.immutable.List
import scala.io.Source
import java.io.File
import java.io.PrintWriter

class CodeGenerator(input: Program, outdir:String, emitter:Emitter) {

    val env = Checker.makeListClass(input.decl, Checker.initial)
	// Don't change the prototype of this method 
	def run: Unit = {	
        val output = generateCode(input.decl,env,List(Symbol("io",ClassType("IO"),Attribute,"IO")))
	    printOutput(output, outdir)       
    }

    def printOutput(out:List[(String,String)], outdir: String) = {
        for (i <- out) {
            val dest = new PrintWriter(new File(outdir+"\\"+i._1+".j" ))
            dest.print(i._2)
            dest.close()
        }
    }
	
    def generateCode(lst:List[Decl],env:List[MyClassType],symTable:List[Symbol]) = 
        lst.map(x=>generateClass(x.asInstanceOf[ClassDecl],symTable))

    def generateClass(decl:ClassDecl,symTable:List[Symbol]) =  {
        val className = decl.name.name
        val classHeader = emitter.emitPROLOG(decl)
        /*val classCInit = emitter.emitCLINIT(decl,List())*/
        val classInit = generateInit(decl)//;println(decl)
        val classFields = generateClassFields(decl)
        val classMethods = generateMethods(decl,symTable)//;println(classMethods) 	//TODO
        val classBody = classHeader + "\n" + classFields + "\n" /*+ classCInit */+ "\n" + classInit + classMethods
        (className,classBody)
    }

    def generateInit(decl:ClassDecl) = {        
        val al = getArrayAttributes(decl)
        //println(al._2)
        emitter.emitINIT(decl,al._2)
    }

    //Return list of attributes with array type
    def getArrayAttributes(decl:ClassDecl) = {
    	var sym = List[Symbol]()
    	decl.decl.foldLeft(("",sym))((a,b) => b match {
        	case VarDecl(id,t) => {
    			t match {
    			  	case ArrayType(size,typ) => (id.name,Symbol(id.name,t,Variable,"")::a._2)
    				case _ => a}
        	}
        	case ConstDecl(id,t,e) => { //TODO
        		e match {
        		  	case IntLiteral(num) => (id.name,Symbol(id.name,t,Variable,num.toString)::a._2) 
        		  	case FloatLiteral(num) => (id.name,Symbol(id.name,t,Variable,num.toString)::a._2)
        		  	case StringLiteral (str) => (id.name,Symbol(id.name,t,Variable,str)::a._2)  
        		  	case BooleanLiteral (b) => (id.name,Symbol(id.name,t,Variable,b.toString)::a._2)  
        		}
        	}
        	case _ => a
    	})
    }

    def generateClassFields(decl:ClassDecl) = {
        val al = getAttributes(decl.decl)
        al.foldLeft("")((a,b) => a + emitter.emitINSTANCEFIELD(b.name,b.typ,b.kind,b.obj.get))
    }

    def getAttributes(decl:List[Decl]):List[Symbol] = decl match {
        case List() => List()
        case head::tail => head match {
            case VarDecl(i,t) => Symbol(i.name,t,Variable,Some(""))::getAttributes(tail)
            case ConstDecl(i,t,e) => {
            	e match {
            	  	case IntLiteral(num) => Symbol(i.name,t,Constant,num.toString)::getAttributes(tail)
            	  	case FloatLiteral(num) => Symbol(i.name,t,Constant,num.toString)::getAttributes(tail)
            	  	case StringLiteral(str) => Symbol(i.name,t,Constant,str)::getAttributes(tail)
            	  	case BooleanLiteral(b) => Symbol(i.name,t,Constant,b.toString)::getAttributes(tail)
            	  	// etc ... NOT FINISH
            	}
            }
            case _ => getAttributes(tail)
        }
    }

    def generateMethods(decl:ClassDecl,sym:List[Symbol]) = {
        val ml = getMethods(decl.decl)//; println(ml)
        ml.foldLeft("")((a,b) => a + generateMethod(b,decl,sym))
    }

    def getMethods(decl:List[Decl]):List[MethodImpDecl] = decl match {
        case List() => List()
        case head::tail => head match {
            case MethodImpDecl(r,n,pl,b) => MethodImpDecl(r,n,pl,b)::getMethods(tail)
            case _ => getMethods(tail)
        }
    }

    def generateMethod(decl:MethodImpDecl,cls:ClassDecl,sym:List[Symbol]) = {//TODO
        val isMain = decl.name.name == "main" && decl.param.length == 0 && cls.name.name == "Main"
        val frame = new Frame(isMain)
        if (decl.returnType == null) {	// constructor
        	var strparams = ""
            	for (i<-0 to decl.param.length-1) {
            	  
            		decl.param(i) match {
            		  	case ParamDecl(n,t) => {
            		  		if (t == IntType) strparams = strparams + t.genCode
            		  		else if (t == FloatType) strparams = strparams + t.genCode
            		  		else if (t == BoolType) strparams = strparams + t.genCode
            		  		else if (t == StringType) strparams = strparams + t.genCode
            		  		else 
            		  			t match {
            		  			  	case ClassType(nam) => strparams = strparams + t.genCode
            		  			  	case ArrayType(s,et) => strparams = strparams + t.genCode
            		  			  	case MethodType(cn,param,rl) => strparams = strparams + t.genCode
            		  			  	case _ =>
            		  			}
            		  	}
            		  	case _ => 
            		}
            	} 	
            	val consMethod = emitter.emitWRITESOMESTRING(".method public <init>("+strparams+")V")
            		        
		        var index = 0
		        if (!isMain) index = frame.getNewIndex
		        	
		        val params = generateParam(decl.param,sym,frame)
		        val startLabel = frame.getStartLabel
		        val endLabel = frame.getEndLabel
		        var notMain = ""
		        if (!isMain) notMain = //emitter.emitWRITESOMESTRING(".var "+index +" is this LMain; from Label"+startLabel.toString+" to Label"+endLabel.toString)
		        	emitter.emitVAR(index,"this",ClassType(decl.name.name),startLabel,endLabel)
		        val label0 = emitter.emitLABEL(startLabel)
		        val aload0 = emitter.emitWRITESOMESTRING("aload_0")
		        val invo = emitter.emitWRITESOMESTRING("invokespecial java/lang/Object/<init>()V")
		        val body = generateStmt(decl.body,cls,params._2,true,frame)
		        val label1 = emitter.emitLABEL(endLabel)
		        val ret = emitter.emitRETURN(null,frame) // constructor not return
		        val limits = emitter.emitLIMITSTACK(frame.getMaxOpStackSize) +
		                     emitter.emitLIMITLOCAL(frame.getMaxIndex)
		        val endmt = emitter.emitENDMETHOD
		        
		        consMethod + notMain + params._1 + label0 + aload0 + invo + body + label1 + ret + limits + endmt
        }
        else { // NOT CONTRUCSTOR
	        val prolog = emitter.emitMETHOD(decl.name.name,MethodType(cls.name.name,Checker.getParamList(decl.param),decl.returnType),isMain,frame)
	        var index = 0
	        if (!isMain) index = frame.getNewIndex
	        	
	        val params = generateParam(decl.param,sym,frame)
	        val startLabel = frame.getStartLabel
	        val endLabel = frame.getEndLabel
	        var notMain = ""
	        if (!isMain) notMain = emitter.emitWRITESOMESTRING(".var "+index +" is this LMain; from Label"+startLabel.toString+" to Label"+endLabel.toString)
	        	//emitter.emitVAR(index,"this",ClassType(decl.name.name),startLabel,endLabel)
	        val label0 = emitter.emitLABEL(startLabel)
	        val body = generateStmt(decl.body,cls,params._2,true,frame)
	        val label1 = emitter.emitLABEL(endLabel)
	        val ret = if (isMain) emitter.emitRETURN(null,frame) else ""
	        val limits = emitter.emitLIMITSTACK(frame.getMaxOpStackSize) +
	                     emitter.emitLIMITLOCAL(frame.getMaxIndex)
	        val endmt = emitter.emitENDMETHOD
	        //println(body)
	        prolog + notMain + params._1 + label0 + body + label1 + ret + limits + endmt	
        }
        
    }

    def generateParam(dl:List[ParamDecl],sym:List[Symbol],frame:Frame) = {
        frame.enterScope(true)
        if (frame.isMain) 
            (emitter.emitVAR(frame.getNewIndex(),"arg","[Ljava/lang/String;",frame.getStartLabel(),frame.getEndLabel()),sym)                   
        else 
            dl.foldLeft(("",sym))((a,b) => {
                val index = frame.getNewIndex
                (a._1 + emitter.emitVAR(index,b.id.name,b.paramType,frame.getStartLabel,frame.getEndLabel),
                 Symbol(b.id.name,b.paramType,Parameter,index.toString)::a._2)
                })
    }
    
    def generateVariable(dl:List[Decl],sym:List[Symbol],frame:Frame) = {
        dl.foldLeft(("",sym))((a,b) => b match {
            case VarDecl(id,t) => {
                val index = frame.getNewIndex
                (a._1 + emitter.emitVAR(index,id.name,t,frame.getStartLabel,frame.getEndLabel),
                 Symbol(id.name,t,Variable,index.toString)::a._2) 
              }
            case ConstDecl(id,t,e) => {
            	//(emitter.emitICONST(1, frame),Symbol("",IntType,Constant,"")::a._2)
            	val index = frame.getNewIndex
                (a._1 + emitter.emitVAR(index,id.name,t,frame.getStartLabel,frame.getEndLabel),
                Symbol(id.name,t,Variable,index.toString)::a._2) 
            }
            case _ => a 
            })
    }

    def handleArrayConstDecl(dl:List[Decl],cls:ClassDecl,sym:List[Symbol],frame:Frame) = {//println(dl)
        dl.foldLeft(("",sym))((a,b) => b match {
            case VarDecl(id,t) => { 
                val index = lookup(id.name,sym).obj.get.toInt
                t match {
                  	case ArrayType(size,typ) => {
                  		size match {
                  		  	case IntLiteral(num) => {//println(typ)
                  		  		typ match {
                  		  		  	case IntType => (emitter.emitICONST(num, frame)+emitter.emitWRITESOMESTRING("newarray int")+emitter.emitASTORE(index, frame),Symbol(id.name,t,Variable,index.toString)::a._2)
                  		  		  	case FloatType => (emitter.emitICONST(num, frame)+emitter.emitWRITESOMESTRING("newarray float")+emitter.emitASTORE(index, frame),Symbol(id.name,t,Variable,index.toString)::a._2)
                  		  		  	case StringType => (emitter.emitICONST(num, frame)+emitter.emitWRITESOMESTRING("anewarray java/lang/String")+emitter.emitASTORE(index, frame),Symbol(id.name,t,Variable,index.toString)::a._2)
                  		  		  	case BoolType => (emitter.emitICONST(num, frame)+emitter.emitWRITESOMESTRING("newarray boolean")+emitter.emitASTORE(index, frame),Symbol(id.name,t,Variable,index.toString)::a._2)
                  		  		  	case ClassType(nam) => (emitter.emitICONST(num, frame)+emitter.emitWRITESOMESTRING("anewarray "+nam)+emitter.emitASTORE(index, frame),Symbol(id.name,t,Variable,index.toString)::a._2) 
                  		  		}	
                  		  	}
                  		  	case _ =>	a//("",Symbol(id.name,t,Variable,index.toString)::a._2)
                  		}
                  	}
                  	case _ =>	a//("", Symbol(id.name,t,Variable,index.toString)::a._2)
                }
              }
            case ConstDecl(id,t,e) => {
            	val index = lookup(id.name,sym).obj.get.toInt
            	val expCode = generateExpression(e,cls,sym,frame) 
            	t match {
            	  	case IntType => (expCode._1+emitter.emitISTORE(index, frame),Symbol(id.name,t,Variable,index.toString)::a._2)
            	  	case FloatType => (expCode._1+emitter.emitI2F(frame)+emitter.emitFSTORE(index, frame),Symbol(id.name,t,Variable,index.toString)::a._2)
            	  	case StringType => (expCode._1+emitter.emitASTORE(index, frame),Symbol(id.name,t,Variable,index.toString)::a._2)
            	  	case BoolType => (expCode._1+emitter.emitASTORE(index, frame),Symbol(id.name,t,Variable,index.toString)::a._2)
            	  	case ClassType(nam) => (expCode._1+emitter.emitASTORE(index, frame),Symbol(id.name,t,Variable,index.toString)::a._2)
            	}
            }
            case _ => a
            })
    }

    def generateStmtList(sl:List[Stmt],cls:ClassDecl,sym:List[Symbol],isBody:Boolean,frame:Frame) = 
        sl.foldLeft("")((a,b) => a + generateStmt(b,cls,sym,isBody,frame))
        
    def generateStmt(stmt:Stmt,cls:ClassDecl,sym:List[Symbol],isBody:Boolean,frame:Frame):String = {
        stmt match {
            case Block(dl,sl) => {
                if (!isBody) frame.enterScope(false)
                val decl = generateVariable(dl,sym,frame)
                val arr = handleArrayConstDecl(dl,cls,decl._2,frame)
                val startLabel = frame.getStartLabel
                val endLabel = frame.getEndLabel
                val label0 = emitter.emitLABEL(startLabel)
                val stmts = generateStmtList(sl,cls,decl._2,false,frame)
                val label1 = emitter.emitLABEL(endLabel)
                frame.exitScope()//;println(decl._1 + label0 + arr._1 + stmts + label1)
                decl._1 + label0 + arr._1 + stmts + label1
            }
            case Assign(lhs,exp) => {
                lhs match {
                    case Id(n) => {
                        val expCode = generateExpression(exp,cls,sym,frame)
                        val symbol = lookup(n,sym)
                        val lhsCode = emitter.emitWRITEVAR(symbol,frame)
                        expCode._1 + lhsCode
                    }
                    case FieldAccess(e,field) => {
                    	val expCode = generateExpression(exp,cls,sym,frame)
                    	val lhsexp = generateExpression(e,cls,sym,frame)
                    	lhsexp._2.typ match {
                    	  	case ClassType(nam) => {
                    	  		val putfield = emitter.emitPUTFIELD(field.name,expCode._2.typ, frame, ClassType(nam))
                    	  		lhsexp._1+expCode._1+putfield
                    	  	}
                    	  	case _ => ""
                    	}        	
                    }
                    case ArrayCell(n,e) => {
                    	val expCode = generateExpression(exp,cls,sym,frame) //integer[] := float
                    	val gn = generateExpression(n,cls,sym,frame)
                    	val lhsCode = emitter.emitWRITEVAR(gn._2, frame)
                    	val ge = generateExpression(e,cls,sym,frame)
                    	
                    	gn._1+ge._1+expCode._1+lhsCode
                    }
                }
            }
            case Call(e,n,pl) => {
                val lhscls = generateExpression(e,cls,sym,frame)              
                val params = pl.foldLeft("")((a,b) => {val res = generateExpression(b,cls,sym,frame);a + res._1})
                val clstyp = lhscls._2.typ.asInstanceOf[ClassType]
                val mth = lookupMethod(n.name,pl.length,clstyp,env)
                val call = emitter.emitINVOKEVIRTUAL(clstyp.classType+"/"+n.name,mth.typ,frame)
             
                var nparams = new StringBuffer()
                mth.typ match {
                  	case MethodType(_,ls,t) => {
                  		//println(pl,ls)
                  		//println(pl(0))
                  		for(i <- 0 to pl.length-1){
                  			pl(i) match {
                  			  	case IntLiteral(_) => {
		                  			if (ls(i) == FloatType)
		                  				nparams.append(generateExpression(pl(i),cls,sym,frame)._1 + emitter.emitI2F(frame))
		                  			else nparams.append(generateExpression(pl(i),cls,sym,frame)._1)                  			  	  
                  			  	}
                  			  	case _ => nparams.append(generateExpression(pl(i),cls,sym,frame)._1)          
                  			}
                  		}
                  	}
                }
                //lhscls._1 + params + call
                //println(nparams)
                lhscls._1+nparams+call
            }
            case Repeat(lststmt,exp) => {
            	frame.enterLoop
            	val newlbl1 = frame.getNewLabel
            	val label1 = emitter.emitLABEL(newlbl1)
            	val dostmt = lststmt.foldLeft("")((a,b) => {val res = generateStmt(b,cls,sym,false,frame); a + res})
            	val continuelabel = frame.getContinueLabel
            	val breaklabel = frame.getBreakLabel
            	val continuelbl = emitter.emitLABEL(continuelabel)
            	val gotolbl1 = emitter.emitGOTO(newlbl1, frame)
            	val expCode = generateExpression(exp,cls,sym,frame)
            	val ifeq = emitter.emitIFTRUE(breaklabel,frame) // strange
            	val breaklbl = emitter.emitLABEL(breaklabel)
            	frame.exitLoop
            	label1 + dostmt + continuelbl + expCode._1 + ifeq + gotolbl1 + breaklbl
            }
            case Return(exp) => {
            	val expCode = generateExpression(exp,cls,sym,frame)
            	//println(expCode)
            	expCode._1+emitter.emitRETURN(expCode._2.typ, frame)
            }
            case For(name, exp, up, exp2, loopstmt) => {  
            	val assignment = generateStmt(Assign(name,exp),cls,sym,false,frame)
            	val newlbl1 = frame.getNewLabel
            	val label1 = emitter.emitLABEL(newlbl1)  
            	val symbol0 = lookup(name.name,sym)
            	val iloadi0 = emitter.emitILOAD(symbol0.obj.get.toInt, frame)
            	val expr2 = emitter.emitICONST(generateExpression(exp2,cls,sym,frame)._2.obj.get.toInt, frame)
            	frame.enterLoop
            	val breaklabel = frame.getBreakLabel
            	up match {
            	  	case true =>	{
	            		val demo = emitter.emitRELOP("<=", IntType, frame)
	            		val ifeq = emitter.emitIFTRUE(breaklabel,frame)
		            	val dostmt = generateStmt(loopstmt,cls,sym,false,frame)
		            	val continuelabel = frame.getContinueLabel
		            	val continuelbl = emitter.emitLABEL(continuelabel)
		            	val symbol = lookup(name.name,sym)
		            	val iloadi = emitter.emitILOAD(symbol.obj.get.toInt, frame)
		            	val iconst1 = emitter.emitICONST(1, frame)
		            	val iadd1 = emitter.emitIADD(frame)
		            	val storei = emitter.emitISTORE(symbol.obj.get.toInt, frame)
		            	val gotolbl1 = emitter.emitGOTO(newlbl1, frame)
		            	val breaklbl = emitter.emitLABEL(breaklabel)
		            	frame.exitLoop
		            	assignment + label1 + iloadi0 + expr2 + demo + ifeq + dostmt + continuelbl + 
		            		iloadi + iconst1 + iadd1 + storei + gotolbl1 + breaklbl
            	  	}
            	  	case _ =>	{
            		  	val demo = emitter.emitRELOP(">=", IntType, frame)
            		  	val ifeq = emitter.emitIFTRUE(breaklabel,frame)
		            	val dostmt = generateStmt(loopstmt,cls,sym,false,frame)
		            	val continuelabel = frame.getContinueLabel
		            	val continuelbl = emitter.emitLABEL(continuelabel)
		            	val symbol = lookup(name.name,sym)
		            	val iloadi = emitter.emitILOAD(symbol.obj.get.toInt, frame)
		            	val iconst1 = emitter.emitICONST(-1, frame)
		            	val iadd1 = emitter.emitIADD(frame)
		            	val storei = emitter.emitISTORE(symbol.obj.get.toInt, frame)
		            	val gotolbl1 = emitter.emitGOTO(newlbl1, frame)
		            	val breaklbl = emitter.emitLABEL(breaklabel)
		            	frame.exitLoop
		            	assignment + label1 + iloadi0 + expr2 + demo + ifeq + dostmt + continuelbl + 
		            		iloadi + iconst1 + iadd1 + storei + gotolbl1 + breaklbl
            	  	}
            	}
            }
            case While(exp,stmt) => {
            	frame.enterLoop
            	val breaklabel = frame.getBreakLabel
            	val continuelabel = frame.getContinueLabel
            	val continuelbl = emitter.emitLABEL(continuelabel)
            	val expCode = generateExpression(exp,cls,sym,frame) 
            	val ifeq = emitter.emitIFTRUE(breaklabel,frame) 
            	val dostmt = generateStmt(stmt,cls,sym,false,frame)
            	val gotocontinuelbl = emitter.emitGOTO(continuelabel, frame)
            	val breaklbl = emitter.emitLABEL(breaklabel)
            	frame.exitLoop // before code for break label
            	continuelbl + expCode._1 + ifeq + dostmt + gotocontinuelbl + breaklbl
            }
            case Break => 	emitter.emitGOTO(frame.getBreakLabel, frame)
            case Continue =>	emitter.emitGOTO(frame.getContinueLabel, frame)
            case If(exp,thenstmt,elsestmt) => elsestmt match {
              	case None => {
	                val expCode = generateExpression(exp,cls,sym,frame) 
	                val newlbl1 = frame.getNewLabel
	                val ifeq = emitter.emitIFTRUE(newlbl1,frame) 
	                val tstmt = generateStmt(thenstmt,cls,sym,false,frame)
	                val label1 = emitter.emitLABEL(newlbl1);
	                expCode._1 + ifeq + tstmt + label1
              	}
              	case Some(es) => {
	            	val expCode = generateExpression(exp,cls,sym,frame) 
	            	val newlbl1 = frame.getNewLabel
	            	val newlbl2 = frame.getNewLabel
	            	val ifeq = emitter.emitIFTRUE(newlbl1,frame)
	            	thenstmt match {
	            	  	case Return(_) => {
			            	val tstmt = generateStmt(thenstmt,cls,sym,false,frame)
			            	val label1 = emitter.emitLABEL(newlbl1);
			            	val estmt = generateStmt(es,cls,sym,false,frame)
			            	val label2 = emitter.emitLABEL(newlbl2);
			            	expCode._1 + ifeq + tstmt + label1 + estmt + label2
	            	  	}
	            	  	case _ => {
			            	val tstmt = generateStmt(thenstmt,cls,sym,false,frame)
			            	val gotolbl2 = emitter.emitGOTO(newlbl2, frame)
			            	val label1 = emitter.emitLABEL(newlbl1);
			            	val estmt = generateStmt(es,cls,sym,false,frame)
			            	val label2 = emitter.emitLABEL(newlbl2);
			            	//println(expCode._1 + ifeq + tstmt + gotolbl2 + label1 + estmt + label2)
			            	expCode._1 + ifeq + tstmt + gotolbl2 + label1 + estmt + label2	            	  	  
	            	  	}
	            	}
              	}
            }
        }
    }

    def generateExpression(exp:Expr,cls:ClassDecl,sym:List[Symbol],frame:Frame):(String,Symbol) = {
        exp match {
            case Id(n) => {
            	val symbol = lookup(n,sym)
            	val expCode = emitter.emitREADVAR(symbol,frame)
            	(expCode,symbol)     				
            }
            case IntLiteral(v) => {
            	val symbol = Symbol("",IntType,Constant,v.toString) 
                val expCode = emitter.emitICONST(v,frame)
                (expCode,symbol)
            }
            case FloatLiteral(v) => {
            	val symbol = Symbol("",FloatType,Constant,v.toString)
            	val expCode = emitter.emitFCONST(v.toString,frame)
            	(expCode,symbol)
            }
            case BooleanLiteral(v) => {
            	val symbol = Symbol("",BoolType,Constant,v.toString)
            	val expCode = emitter.emitICONST(v.toString,frame)
            	(expCode,symbol)
            }
            case StringLiteral(v) => {
            	val symbol = Symbol("",StringType,Constant,v.toString)
              	val expCode = emitter.emitLDC("\""+v.toString()+"\"",frame)
              	(expCode,symbol)
            }
            case NullLiteral => (emitter.emitNULLCONST(frame),Symbol("",ClassType(""),Constant,"null"))
            case SelfLiteral => (emitter.emitALOAD(0, frame),Symbol("",ClassType(cls.name.name),Constant,"self"))
            case UnaryOp(op,e) => {
            	op match {
            	  	case "+" => {
            	  		generateExpression(e,cls,sym,frame)
            	  	}
            	  	case "-" => {
            	  		val value = generateExpression(e,cls,sym,frame)
            	  		value match {
            	  		  	case (expCode,Symbol(_,typ,Constant,_)) => {
            	  		  		if (typ == IntType)
            	  		  			 (expCode+emitter.emitNEGOP("-", IntType, frame),Symbol("",typ,Constant,""))
            	  		  		else
            	  		  			 (expCode+emitter.emitNEGOP("-", FloatType, frame),Symbol("",typ,Constant,""))
            	  		  	}
            	  		}
            	  	}
            	  	case "!" => {
            	  		val value = generateExpression(e,cls,sym,frame)
            	  		val neg = emitter.emitI("!", BoolType, frame)
            	  		(value._1+neg, Symbol("", BoolType, Constant, ""))            	  	  
            	  	}
            	  	case _ => {
            	  		throw  IllegalOperandException
            	  	}
            	}
            }
            case FieldAccess(e, field) => generateExpression(e, cls, sym, frame) match {
            	case (eCode, Symbol(_, ClassType(cName), _, _)) => {
            		val fieldSymbol = lookupField(field.name, ClassType(cName), env)
            				val expCode = eCode + emitter.emitGETFIELD(field.name, fieldSymbol.typ, frame, ClassType(cName))
            				(expCode, Symbol("", fieldSymbol.typ, Constant, Some("")))
            	}
            	case _ => ("", Symbol("", IntType, Constant, ""))
            }
            case NewExpr(id,pl) => { 	
            	val params = pl.foldLeft("")((a,b) => {val res = generateExpression(b,cls,sym,frame);a + res._1})
            	val symbol = Symbol("",ClassType(""),Constant,"")
            	val writeNew = emitter.emitWRITESOMESTRING("new "+id.name)
            	val writeDup = emitter.emitDUP(frame)
            	var strparams = ""
            	for (i<-0 to pl.length-1) {//println(pl(i))
            		pl(i) match {
            			case IntLiteral(_) => strparams = strparams + IntType.genCode
            			case FloatLiteral(_) => strparams = strparams + FloatType.genCode
            			case BooleanLiteral(_) => strparams = strparams + BoolType.genCode
            			case StringLiteral(_) => strparams = strparams + StringType.genCode
            			//case ArrayCell(d,et) => strparams = strparams + ArrayType(d,et).genCode
            			case _ => strparams = strparams + FloatType.genCode // OWE CLASS TYPE, ARRAY TYPE
            		}
            	}
            	
            	val writecall = emitter.emitWRITESOMESTRING("invokespecial "+id.name+"/<init>("+strparams+")V")
            	(writeNew+writeDup+params+writecall,symbol)
            }
            case CallExpr(e,n,pl) => {	// OWE TYPE OF PARAMETERS
            	val lhscls = generateExpression(e,cls,sym,frame)
            	val params = pl.foldLeft("")((a,b) => {val res = generateExpression(b,cls,sym,frame);a + res._1})
            	val clstyp = lhscls._2.typ.asInstanceOf[ClassType]//;println(params)
                val mth = lookupMethod(n.name,pl.length,clstyp,env)//;println(mth)
                
                var nparams = new StringBuffer()
                mth.typ match {
                  	case MethodType(_,ls,t) => {
                  		//println(pl,ls)
                  		//println(pl(0))
                  		for(i <- 0 to pl.length-1){
                  			pl(i) match {
                  			  	case IntLiteral(_) => {
		                  			if (ls(i) == FloatType)
		                  				nparams.append(generateExpression(pl(i),cls,sym,frame)._1 + emitter.emitI2F(frame))
		                  			else nparams.append(generateExpression(pl(i),cls,sym,frame)._1)                  			  	  
                  			  	}
                  			  	case _ => nparams.append(generateExpression(pl(i),cls,sym,frame)._1)          
                  			}
                  		}
                  	}
                }
                //println(nparams)
            	val call = emitter.emitINVOKEVIRTUAL(clstyp.classType+"/"+n.name,mth.typ,frame)
            	mth.typ match {
            	  	case MethodType(nam,_,t) => (lhscls._1 + nparams + call,Symbol(nam,t,Constant,""))
            	  	case _ => (lhscls._1 + params + call,Symbol("",IntType,Constant,""))	// NEVER HAPPEN
            	}
            }
            case BinaryOp(op, left, right) => {
            	val leftValue = generateExpression(left,cls,sym,frame)
            	val rightValue = generateExpression(right,cls,sym,frame)
            	op match {  
            	  	case "+" => {
	            	  	if (leftValue._2.typ == IntType && rightValue._2.typ == IntType) {
	            	  		  val symbol = Symbol("",IntType,Constant,"") 
	            	  		  val expCode = emitter.emitADDOP("+",IntType,frame)
	            	  		  (leftValue._1+rightValue._1+expCode,symbol)
	            	  	} else {
	            	  		  val symbol = Symbol("",FloatType,Constant,"") 
	            	  		  val expCode = emitter.emitADDOP("+",FloatType,frame)
	            	  		  if (leftValue._2.typ == IntType && rightValue._2.typ == FloatType) (leftValue._1+emitter.emitI2F(frame)+rightValue._1+expCode,symbol)
	            	  		  else if (leftValue._2.typ == FloatType && rightValue._2.typ == IntType) (leftValue._1+rightValue._1+emitter.emitI2F(frame)+expCode,symbol)
	            	  		  else	(leftValue._1+rightValue._1+expCode,symbol)
	            	  	}            	  	   
            	  	}
            	  	case "-" => {
	            	  	if (leftValue._2.typ == IntType && rightValue._2.typ == IntType) {
	            	  		  val symbol = Symbol("",IntType,Constant,"") 
	            	  		  val expCode = emitter.emitADDOP("-",IntType,frame)		            	  		
	            	  		  (leftValue._1+rightValue._1+expCode,symbol)
	            	  	} else {
	            	  		  val symbol = Symbol("",FloatType,Constant,"") 
	            	  		  val expCode = emitter.emitADDOP("-",FloatType,frame)
	            	  		  if (leftValue._2.typ == IntType && rightValue._2.typ == FloatType) (leftValue._1+emitter.emitI2F(frame)+rightValue._1+expCode,symbol)
	            	  		  else if (leftValue._2.typ == FloatType && rightValue._2.typ == IntType) (leftValue._1+rightValue._1+emitter.emitI2F(frame)+expCode,symbol)
	            	  		  else	(leftValue._1+rightValue._1+expCode,symbol)
	            	  	}            	  	   
            	  	}
            	  	case "*" => {
	            	  	if (leftValue._2.typ == IntType && rightValue._2.typ == IntType) {
	            	  		  val symbol = Symbol("",IntType,Constant,"") 
	            	  		  val expCode = emitter.emitMULOP("*",IntType,frame)
		            	  		
	            	  		  (leftValue._1+rightValue._1+expCode,symbol)
	            	  	} else {
	            	  		  val symbol = Symbol("",FloatType,Constant,"") 
	            	  		  val expCode = emitter.emitMULOP("*",FloatType,frame)
	            	  		  if (leftValue._2.typ == IntType && rightValue._2.typ == FloatType) (leftValue._1+emitter.emitI2F(frame)+rightValue._1+expCode,symbol)
	            	  		  else if (leftValue._2.typ == FloatType && rightValue._2.typ == IntType) (leftValue._1+rightValue._1+emitter.emitI2F(frame)+expCode,symbol)
	            	  		  else	(leftValue._1+rightValue._1+expCode,symbol)
	            	  	}            	  	   
            	  	}
            	  	case "/" => {
            	  		val symbol = Symbol("",FloatType,Constant,"") 
            	  		val expCode = emitter.emitMULOP("/",FloatType,frame)
            	  		if (leftValue._2.typ == IntType && rightValue._2.typ == IntType)
            	  			(leftValue._1+emitter.emitI2F(frame)+rightValue._1+emitter.emitI2F(frame)+expCode,symbol)
            	  		else if (leftValue._2.typ == IntType && rightValue._2.typ == FloatType)
            	  			(leftValue._1+emitter.emitI2F(frame)+rightValue._1+expCode,symbol)
            	  		else if (leftValue._2.typ == FloatType && rightValue._2.typ == IntType)
            	  			(leftValue._1+rightValue._1+emitter.emitI2F(frame)+expCode,symbol)
            	  		else	(leftValue._1+rightValue._1+expCode,symbol)
            	  	}  
            	  	case "\\" => {
	            	  	val symbol = Symbol("",IntType,Constant,"") 
	            	  	val expCode = emitter.emitMULOP("\\",IntType,frame)	  		
	            	  	(leftValue._1+rightValue._1+expCode,symbol)             	  	  
            	  	}            	  	
            	  	case "%" => {
	            	  	val symbol = Symbol("",IntType,Constant,"") 
	            	  	val expCode = emitter.emitMULOP("%",IntType,frame)
	            	  	(leftValue._1+rightValue._1+expCode,symbol)            	  		  
            	  	}
            	  	case "==" => {
            	  		if (leftValue._2.typ == IntType || leftValue._2.typ == BoolType) {
		            	  	val symbol = Symbol("",BoolType,Constant,"") 
		            	  	val expCode = emitter.emitEQOP("==",frame)	            	  		
		            	  	(leftValue._1+rightValue._1+expCode,symbol)            	  		  
            	  		}else { // string type
		            	  	val symbol = Symbol("",BoolType,Constant,"") 
		            	  	val expCode = emitter.emitEQOPR("==",frame)	            	  		
		            	  	(leftValue._1+rightValue._1+expCode,symbol)            	  		  
            	  		}
            	  	}
            	  	case "<" => {
	            	  	if (leftValue._2.typ == IntType && rightValue._2.typ == IntType) {
	            	  		  val symbol = Symbol("",BoolType,Constant,"") 
	            	  		  val expCode = emitter.emitRELOP("<",IntType,frame)  		
	            	  		  (leftValue._1+rightValue._1+expCode,symbol)
	            	  	} else {
	            	  		  val symbol = Symbol("",BoolType,Constant,"") 
	            	  		  val expCode = emitter.emitRELOP("<",FloatType,frame)
	            	  		  if (leftValue._2.typ == IntType && rightValue._2.typ == FloatType)	(leftValue._1+emitter.emitI2F(frame)+rightValue._1+expCode,symbol)
	            	  		  else if (leftValue._2.typ == FloatType && rightValue._2.typ == IntType)(leftValue._1+rightValue._1+emitter.emitI2F(frame)+expCode,symbol)
	            	  		  else 	(leftValue._1+rightValue._1+expCode,symbol)
	            	  	}            	  	   
            	  	}
            	  	case ">" => {
	            	  	if (leftValue._2.typ == IntType && rightValue._2.typ == IntType) {
	            	  		  val symbol = Symbol("",BoolType,Constant,"") 
	            	  		  val expCode = emitter.emitRELOP(">",IntType,frame)  		
	            	  		  (leftValue._1+rightValue._1+expCode,symbol)
	            	  	} else {
	            	  		  val symbol = Symbol("",BoolType,Constant,"") 
	            	  		  val expCode = emitter.emitRELOP(">",FloatType,frame)
	            	  		  if (leftValue._2.typ == IntType && rightValue._2.typ == FloatType)	(leftValue._1+emitter.emitI2F(frame)+rightValue._1+expCode,symbol)
	            	  		  else if (leftValue._2.typ == FloatType && rightValue._2.typ == IntType)(leftValue._1+rightValue._1+emitter.emitI2F(frame)+expCode,symbol)
	            	  		  else 	(leftValue._1+rightValue._1+expCode,symbol)
	            	  	}            	  	   
            	  	} 
            	  	case "<=" => {
	            	  	if (leftValue._2.typ == IntType && rightValue._2.typ == IntType) {
	            	  		  val symbol = Symbol("",BoolType,Constant,"") 
	            	  		  val expCode = emitter.emitRELOP("<=",IntType,frame)  		
	            	  		  (leftValue._1+rightValue._1+expCode,symbol)
	            	  	} else {
	            	  		  val symbol = Symbol("",BoolType,Constant,"") 
	            	  		  val expCode = emitter.emitRELOP("<=",FloatType,frame)
	            	  		  if (leftValue._2.typ == IntType && rightValue._2.typ == FloatType)	(leftValue._1+emitter.emitI2F(frame)+rightValue._1+expCode,symbol)
	            	  		  else if (leftValue._2.typ == FloatType && rightValue._2.typ == IntType)(leftValue._1+rightValue._1+emitter.emitI2F(frame)+expCode,symbol)
	            	  		  else 	(leftValue._1+rightValue._1+expCode,symbol)
	            	  	}            	  	   
            	  	}             	  	
            	  	case ">=" => {
	            	  	if (leftValue._2.typ == IntType && rightValue._2.typ == IntType) {
	            	  		  val symbol = Symbol("",BoolType,Constant,"") 
	            	  		  val expCode = emitter.emitRELOP(">=",IntType,frame)  		
	            	  		  (leftValue._1+rightValue._1+expCode,symbol)
	            	  	} else {
	            	  		  val symbol = Symbol("",BoolType,Constant,"") 
	            	  		  val expCode = emitter.emitRELOP(">=",FloatType,frame)
	            	  		  if (leftValue._2.typ == IntType && rightValue._2.typ == FloatType)	(leftValue._1+emitter.emitI2F(frame)+rightValue._1+expCode,symbol)
	            	  		  else if (leftValue._2.typ == FloatType && rightValue._2.typ == IntType)(leftValue._1+rightValue._1+emitter.emitI2F(frame)+expCode,symbol)
	            	  		  else 	(leftValue._1+rightValue._1+expCode,symbol)
	            	  	}            	  	   
            	  	} 
            	  	case "<>" => {
            	  		if (leftValue._2.typ == IntType || leftValue._2.typ == BoolType) {
		            	  	val symbol = Symbol("",BoolType,Constant,"") 
		            	  	val expCode = emitter.emitEQOP("<>",frame)	            	  		
		            	  	(leftValue._1+rightValue._1+expCode,symbol)            	  		  
            	  		}else { // string type
		            	  	val symbol = Symbol("",BoolType,Constant,"") 
		            	  	val expCode = emitter.emitEQOPR("<>",frame)	            	  		
		            	  	(leftValue._1+rightValue._1+expCode,symbol)            	  		  
            	  		}
            	  	}
            	  	case "&&" => {
	            	  	val symbol = Symbol("",BoolType,Constant,"") 
	            	  	val expCode = emitter.emitANDOP(frame)
	            	  	(leftValue._1+rightValue._1+expCode,symbol)            	  	  
            	  	}
            	  	case "||" => {
	            	  	val symbol = Symbol("",BoolType,Constant,"") 
	            	  	val expCode = emitter.emitOROP(frame)	
	            	  	(leftValue._1+rightValue._1+expCode,symbol)            	  	  
            	  	}
            	  	case "^" => {
            	  		(emitter.emitWRITESOMESTRING("new java/lang/StringBuilder")+emitter.emitDUP(frame)+
            	  		    emitter.emitWRITESOMESTRING("invokespecial java/lang/StringBuilder/<init>()V")+leftValue._1+
            	  		    emitter.emitWRITESOMESTRING("invokevirtual java/lang/StringBuilder/append(Ljava/lang/String;)Ljava/lang/StringBuilder;")+rightValue._1+
            	  		    emitter.emitWRITESOMESTRING("invokevirtual java/lang/StringBuilder/append(Ljava/lang/String;)Ljava/lang/StringBuilder;")+
            	  		    emitter.emitWRITESOMESTRING("invokevirtual java/lang/StringBuilder/toString()Ljava/lang/String;"),Symbol("",StringType,Constant,""))
            	  	}
            	}
            }
            case ArrayCell(n,e) => {// NOT FINISH CLASS TYPE
            	val gn = generateExpression(n,cls,sym,frame)
            	val ge = generateExpression(e,cls,sym,frame)
            	gn._2.typ match {
            	  	case ArrayType(_,t) => {
            	  		if (t == IntType)	
            	  			(gn._1+ge._1+emitter.emitTALOAD(IntType, frame),Symbol("",IntType,Constant,""))
            	  		else if (t == FloatType) (gn._1+ge._1+emitter.emitTALOAD(FloatType, frame),Symbol("",FloatType,Constant,""))
            	  		else if (t == StringType) (gn._1+ge._1+emitter.emitTALOAD(StringType, frame),Symbol("",StringType,Constant,""))
            	  		else if (t == BoolType) (gn._1+ge._1+emitter.emitTALOAD(BoolType, frame),Symbol("",BoolType,Constant,""))
            	  		else // CLASS TYPE
            	  			(gn._1+ge._1+emitter.emitTALOAD(ClassType(""), frame),Symbol("",ClassType(""),Constant,""))	
            	  	}
            	}
            }
            case _ => { 
            	("",Symbol("",IntType,Constant,""))
            }
        }
    }

    def lookup(n:String,sym:List[Symbol]):Symbol = sym match {
        case List() => throw Undeclared(Identifier,n)
        case head::tail => if (n == head.name) head else lookup(n,tail)
    }

    def lookupField(n:String,cls:ClassType,env:List[MyClassType]):Symbol =
        env match {
            case List() => throw Undeclared(Attribute,n)
            case head::tail => if (head.name == cls.classType) {
                                    val res = getField(n,head.lstAtt)
                                    res match {
                                        case Some(s) => s
                                        case None =>  if (head.parent == null||head.parent == "")
                                                            throw Undeclared(Attribute,n)
                                                      else lookupField(n,ClassType(head.parent),env)
                                        }
                                } else lookupField(n,cls,tail)
        }

    def lookupMethod(n:String,dim:Integer,cls:ClassType,env:List[MyClassType]):Symbol =
        env match {
            case List() => throw Undeclared(Method,n)
            case head::tail => if (head.name == cls.classType) {
                                    val res = getMethod(n,dim,head.lstMethod)
                                    res match {
                                        case Some(s) => s
                                        case None =>  if (head.parent == null||head.parent == "")
                                                            throw Undeclared(Method,n)
                                                      else lookupMethod(n,dim,ClassType(head.parent),env)
                                    }
                                } else lookupMethod(n,dim,cls,tail)
        }

    def getField(n:String,lst:List[Symbol]):Option[Symbol] =
        lst match {
            case List() => None
            case head::tail => if (n == head.name) Some(head) else getField(n,tail)
        }

    def getMethod(n:String,dim:Integer,lst:List[Symbol]):Option[Symbol] =
        lst match {
            case List() => None
            case head::tail => if (n == head.name && dim == head.typ.asInstanceOf[MethodType].params.length) Some(head) else getMethod(n,dim,tail)
        }
}

/**
* 	Emitter.scala
*/

import java.text.DecimalFormat

class Emitter(machine:MachineCode) {
	
	val END="\n"
	
	def emitICONST(i:Integer,frame:Frame) = {	
		frame.push()
		if (i == -1)
			machine.emitICONST(i)	
		else if (i >= 0 && i <= 5)
			machine.emitICONST(i)
		else if (i >= -128 && i <= 127) 
			machine.emitBIPUSH(i)
		else if (i >= -32768 && i <= 32767) 
			machine.emitSIPUSH(i)
		else
			machine.emitLDC(i.toString)	
	}
	def emitNULLCONST(frame:Frame) = {
		frame.push()
		machine.emitNULLCONST
	}
	def emitICONST(in: String,frame:Frame):String = {
		if (in == "true")
			emitICONST(1,frame)
		else if (in == "false")
			emitICONST(0,frame)
		else {
			try {
				emitICONST(Integer.parseInt(in),frame)
			} catch  {
				case e:NumberFormatException => throw  IllegalOperandException
			}
		}
	}
	
	def emitFCONST(in: String,frame:Frame) =  {		
		try {
			val f = in.toFloat
			val myFormatter = new DecimalFormat("###0.0###")
			val rst = myFormatter.format(f)
			frame.push()
			machine.emitFCONST(rst)
		} catch {
				case e:NumberFormatException => throw  IllegalOperandException
			}
	}
	
	def emitLDC(in: String,frame:Frame) = {
		frame.push()
		machine.emitLDC(in)	
	}
	
	def emitREADVAR(sym: Symbol,frame:Frame,cls: ClassType = null) = {		
		if (sym.kind == Variable || sym.kind == Parameter) {
			if (sym.typ == IntType || sym.typ == BoolType) 
				emitILOAD(sym.obj.get.toInt,frame)
			else if (sym.typ == FloatType )
				emitFLOAD(sym.obj.get.toInt,frame)
			else 
				emitALOAD(sym.obj.get.toInt,frame)
		} else if (sym.kind == Attribute && sym.typ == ClassType("IO") && sym.name == "io") {
			emitGETSTATIC(sym.name,sym.typ,frame, ClassType(sym.obj.get) )
		} else{
			emitGETFIELD(sym.name, sym.typ,frame, ClassType(sym.obj.get) )
		  }
	}
	
	def emitREADVAR2(sym: Symbol,frame:Frame) = 
		sym.typ match {
			case ArrayType(dim,eT) => emitTALOAD(eT,frame)
			case _ => {		
			if (sym.kind == Variable || sym.kind == Parameter) {				
				if (sym.typ == IntType || sym.typ == BoolType) 
					emitILOAD(sym.obj.get.toInt,frame)
				else 
					emitFLOAD(sym.obj.get.toInt,frame)
			} else if (sym.kind == Attribute && sym.typ == ClassType("IO") && sym.name == "io") {
				emitGETSTATIC(sym.name,sym.typ,frame,ClassType(sym.obj.get))
			} else 
				emitGETFIELD(sym.name,sym.typ,frame,ClassType(sym.obj.get))
		}
	}
	
	def emitWRITEVAR(sym: Symbol,frame:Frame,cls:ClassType=null) = 
		sym.typ match {
			case ArrayType(dim,eT) => emitTASTORE(eT,frame)
			case _ => {
			if (sym.kind == Variable || sym.kind == Parameter) {
				if (sym.typ == IntType || sym.typ == BoolType) 
					emitISTORE(sym.obj.get.toInt,frame)
				else if (sym.typ == StringType)
					emitASTORE(sym.obj.get.toInt,frame)
				else if (sym.typ == FloatType)
					emitFSTORE(sym.obj.get.toInt,frame)
				else
					emitASTORE(sym.obj.get.toInt,frame)
			} else if (sym.kind == Attribute && sym.typ == ClassType("IO") && sym.name == "io") {
				emitPUTSTATIC(sym.name,sym.typ,frame,ClassType(sym.obj.get))
			} else
				emitPUTFIELD(sym.name,sym.typ,frame,ClassType(sym.obj.get))
		}
	}
			
	def emitILOAD(in:Integer,frame:Frame) = {
		frame.push()
		machine.emitILOAD(in)
	}
	
	def emitIADD(frame: Frame) = {
		frame.push()
		machine.emitIADD
	}
	
	def emitFLOAD(in: Integer,frame:Frame) = {
		frame.push()
		machine.emitFLOAD(in)
	}	
	
	def emitISTORE(in: Integer,frame:Frame) = {
		frame.pop()
		machine.emitISTORE(in)
	}
	
	def emitFSTORE(in: Integer,frame:Frame) =  {
		frame.pop()
		machine.emitFSTORE(in)
	}

	def emitALOAD(in: Integer,frame:Frame) = {
		frame.push()
		machine.emitALOAD(in)
	}	

	def emitASTORE(in: Integer,frame:Frame) = {
		frame.pop()	
		machine.emitASTORE(in)
		
	}	

	def emitTALOAD(in: Type,frame:Frame) = {
		frame.pop()
		if (in == IntType)
			machine.emitIALOAD
		else if (in == FloatType)
			machine.emitFALOAD
		else if (in == StringType)
			machine.emitAALOAD
		else if (in == BoolType)
			machine.emitBALOAD
		else
			""
	}	

	def emitTASTORE(in: Type,frame:Frame) = {
		//frame.pop()
		frame.pop()
		frame.pop()
		if (in == IntType)
			machine.emitIASTORE
		else if (in == FloatType)
			machine.emitFASTORE
		else if (in == StringType)
			machine.emitAASTORE
		else if (in == BoolType)
			machine.emitBASTORE
		else ""
	}

	def emitADDOP(lexeme: String,in: Type,frame:Frame)=  {
		frame.pop() 
		if (lexeme == "+") {
			if (in == IntType)
				machine.emitIADD
			else 
				machine.emitFADD
		} else 
			if (in == IntType)
				machine.emitISUB
			else 
				machine.emitFSUB
	}
	
	def emitMULOP(lexeme: String,in: Type,frame:Frame) = {
		frame.pop()
		if (lexeme == "*") {
			if (in == IntType)
				machine.emitIMUL
			else 
				machine.emitFMUL
		} else if (lexeme == "/") {
			if (in == IntType)
				machine.emitIDIV
			else 
				machine.emitFDIV		  
		} else if (lexeme == "%")	machine.emitIREM
		else if (lexeme == "\\")	machine.emitIDIV
	}

	def emitANDOP(frame:Frame) = {
		frame.pop()
		machine.emitIAND
	}	
	
	def emitOROP(frame:Frame) = {
		frame.pop()
		machine.emitIOR
	}
	
	def emitEQOP(lexeme: String,label:Integer,frame:Frame) = {
		frame.pop()
		frame.pop()
		if (lexeme == "==") 
			machine.emitIFICMPNE(label) 
		else 
			machine.emitIFICMPEQ(label)		
	}

	
	def emitEQOP(lexeme: String,frame:Frame):String = {
		val label = frame.getNewLabel
		val label1 = frame.getNewLabel
		var buff = new StringBuffer()
		buff.append(emitEQOP(lexeme,label,frame))
		buff.append(emitICONST(1,frame))
		frame.pop()
		buff.append(emitGOTO(label1,frame))
		buff.append(emitLABEL(label))
		buff.append(emitICONST(0,frame))
		buff.append(emitLABEL(label1))
		buff.toString()	
	}
	
	def emitEQOPR(lexeme: String,label:Integer,frame:Frame) = {
		frame.pop()
		frame.pop()
		if (lexeme == "==") 
			machine.emitWRITESOMESTRING("if_acmpne Label" + label) 
		else 
			machine.emitWRITESOMESTRING("if_acmpeq Label" + label)
	}

	
	def emitEQOPR(lexeme: String,frame:Frame):String = {
		val label = frame.getNewLabel
		val label1 = frame.getNewLabel
		var buff = new StringBuffer()
		buff.append(emitEQOPR(lexeme,label,frame))
		buff.append(emitICONST(1,frame))
		frame.pop()
		buff.append(emitGOTO(label1,frame))
		buff.append(emitLABEL(label))
		buff.append(emitICONST(0,frame))
		buff.append(emitLABEL(label1))
		buff.toString()	
	}
	
	def emitRELOP(lexeme: String,in: Type,label:Integer,frame:Frame) = {
		frame.pop()
		frame.pop()
		if (in == IntType) 			
			if (lexeme == ">=")
				machine.emitIFICMPLT(label)
			else if (lexeme == ">")
				machine.emitIFICMPLE(label)
			else if (lexeme == "<=")
			 	machine.emitIFICMPGT(label)
			else
				machine.emitIFICMPGE(label)
		else 
			if (lexeme == ">")
				machine.emitFCMPL + machine.emitIFLE(label)		
			 else if (lexeme == "<") 
				machine.emitFCMPG + machine.emitIFGE(label)
			 else if (lexeme == ">=") 
				machine.emitFCMPL + machine.emitIFLT(label)
			 else 
			  	machine.emitFCMPG + machine.emitIFGT(label)
	}

	def emitRELOP(lexeme: String,in: Type, frame: Frame):String = {
		
		val label = frame.getNewLabel
		val label1 = frame.getNewLabel
		var buff = new StringBuffer()
		buff.append(emitRELOP(lexeme,in,label,frame))
		buff.append(emitICONST(1,frame))
		frame.pop()
		buff.append(emitGOTO(label1,frame))
		buff.append(emitLABEL(label))
		buff.append(emitICONST(0,frame))
		buff.append(emitLABEL(label1))
		buff.toString()
	}
	
	def emitI(lexeme: String, in: Type, frame: Frame):String = {	// operation !
		val label = frame.getNewLabel
		val label1 = frame.getNewLabel
		var buff = new StringBuffer()
		buff.append(machine.emitIFNE(label))
		buff.append(emitICONST(1,frame))
		frame.pop()
		buff.append(emitGOTO(label1,frame))
		buff.append(emitLABEL(label))
		buff.append(emitICONST(0,frame))
		buff.append(emitLABEL(label1))
		buff.toString()
	}
	
	def emitIFTRUE(label: Integer,frame:Frame) = {
		frame.pop()
		//machine.emitIFGT(label)
		machine.emitIFEQ(label)
	}
		
	def emitIFFALSE(label: Integer,frame:Frame) = {
		frame.pop()
		machine.emitIFLE(label)
	}
		
	def emitNEGOP(lexeme: String,in: Type,frame:Frame) = {
		if (lexeme == "!" || lexeme == "-") {
			if (in == IntType || in == BoolType)
				machine.emitINEG
			else
				machine.emitFNEG
		}
		else
			""
	}
	
	def emitGETSTATIC(lexeme: String, in: Type,frame:Frame,cls: ClassType) = {
		frame.push()
		machine.emitGETSTATIC(cls.classType+"."+lexeme,in.genCode)
	}
	
	def emitPUTSTATIC(lexeme: String, in: Type,frame:Frame,cls: ClassType) = {
		frame.pop()
		machine.emitPUTSTATIC(cls.classType+"."+lexeme,in.genCode)
	}	
	

	def emitGETFIELD(lexeme: String, in: Type,frame:Frame, cls: ClassType) = {
		frame.push()
		machine.emitGETFIELD(cls.classType+"."+lexeme,in.genCode)
	}
	 
	def emitPUTFIELD(lexeme: String, in: Type,frame:Frame,cls: ClassType) = {
		frame.pop()
		machine.emitPUTFIELD(cls.classType+"."+lexeme,in.genCode)
	}
	def emitGOTO(label: Integer,frame:Frame) = {
		machine.emitGOTO(label)
	}
	
	def emitDUP(frame:Frame) = {
		frame.push()
		machine.emitDUP
	}

	def emitDUPX2(frame:Frame) = {
		frame.push()
		machine.emitDUPX2 
	}	
	def emitPOP(frame:Frame) = {
		frame.pop()
		machine.emitPOP 
	}
	
	def emitI2F(frame:Frame) = 
		machine.emitI2F
	
	
	def emitNEWARRAY(in: Type,frame:Frame) = 
		machine.emitNEWARRAY(in.genCode)
	
	
	def emitINVOKESTATIC(lexeme: String,in: Type,frame:Frame) = {
		val ft = in.asInstanceOf[MethodType]

		(1 to ft.params.length).foreach( _ => frame.pop)
		
		if (ft.returnType != null && ft.returnType != VoidType)
			frame.push()		
		machine.emitINVOKESTATIC(lexeme,in.genCode)
	}
	
	def emitINVOKEVIRTUAL(lexeme: String,in: Type,frame:Frame) = {
		val ft = in.asInstanceOf[MethodType]
		
		(0 to ft.params.length).foreach( _ => frame.pop)
		
		if (ft.returnType != null && ft.returnType != VoidType)
			frame.push()
		machine.emitINVOKEVIRTUAL(lexeme,in.genCode)
	}

	def emitINVOKESPECIAL(frame:Frame) =
		machine.emitINVOKESPECIAL
	
	
	def emitRETURN(in: Type,frame:Frame) = {
		if (in == null){
			machine.emitRETURN 
		} else {
			frame.pop()
			if (in == IntType || in == BoolType)
				machine.emitIRETURN
			else if (in == StringType)
				machine.emitARETURN
			else if (in == FloatType)
				machine.emitFRETURN
			else ""
		}
	}
	
	def emitLIMITSTACK(in: Integer) =
		machine.emitLIMITSTACK(in)
	
	
	def emitLIMITLOCAL(in: Integer) =
		machine.emitLIMITLOCAL(in)
	

	def emitVAR(in: Integer,varName: String, inType: Type, fromLabel: Integer, toLabel: Integer) =
		machine.emitVAR(in, varName,inType.genCode,fromLabel,toLabel)
	
	def emitVAR(in: Integer,varName: String, inType: String, fromLabel: Integer, toLabel: Integer) =
		machine.emitVAR(in,varName,inType,fromLabel,toLabel)
		
	def emitVAR(in: Integer, varname: String, inType: Type) = 
		machine.emitVAR(in,varname,inType.genCode)
	
	
	def emitMETHOD(lexeme: String, in: String, isStatic: Boolean,frame:Frame) = 
		machine.emitMETHOD(lexeme,in,isStatic)
	

	def emitMETHOD(lexeme: String, in: Type, isStatic: Boolean,frame:Frame):String = {		
		if (frame.isMain)
			emitMETHOD(lexeme,"([Ljava/lang/String;)V",true ,frame) 
		else 
			emitMETHOD(lexeme,in.genCode ,isStatic,frame) 
	}	

	def emitENDMETHOD() = 
		machine.emitENDMETHOD
	
	
	def emitLABEL(in: Integer) = 
		machine.emitLABEL(in)
	
	
	def emitPROLOG(decl:ClassDecl) = {
		val cls = machine.emitCLASS(decl.name.name)
        val parent = machine.emitSUPER(if (decl.parent != null) decl.parent.name else "java/lang/Object")
        //val sour = ".source " + source + END
        cls + parent //+ sour
	}
	
	def emitWRITESOMESTRING(lexeme: String) = machine.emitWRITESOMESTRING(lexeme)
	
	def emitSTATICFIELD(lexeme: String, in: Type, kind: Kind, value: String = "") = {
		machine.emitSTATICFIELD(lexeme,in.genCode,kind == Constant,value) 
	}
	
	def emitINSTANCEFIELD(lexeme: String, in: Type, kind: Kind, value: String = "") = {
		machine.emitINSTANCEFIELD(lexeme,in.genCode,kind == Constant,value) 
	}
	
	def emitCLINIT(decl:ClassDecl, in:List[Symbol]) = {
		var result = new StringBuffer()
		val prs:List[Type] = List()
		val tmp = MethodType(decl.name.name,prs,VoidType)
		val frame = new Frame(false)
		frame.enterScope(true)
		result.append(emitMETHOD("<clinit>",tmp,true,frame))
		result.append(emitLIMITSTACK(1))
		result.append(emitLIMITLOCAL(0))
		//if (CodeGenerator.DEBUG) System.out.println(in.size())
		in.foreach(sym => {
			val at = sym.typ.asInstanceOf[ArrayType]
			result.append(emitICONST(at.dimen.value,frame))
			result.append(emitNEWARRAY(at.eleType,frame))
			result.append(emitPUTSTATIC(sym.name,sym.typ,frame,ClassType(decl.name.name)))
		})
		result.append(emitRETURN(null,frame))
		result.append(emitENDMETHOD())
		frame.exitScope()
		//in.clear()
		result.toString()
	}		
		
	def emitINIT(decl:ClassDecl,in:List[Symbol]) = {
		var result = new StringBuffer()

		val tmp = MethodType(decl.name.name,List(),VoidType)
		val frame = new Frame(false)
		frame.enterScope(true)
		result.append(emitMETHOD("<init>",tmp,false,frame))
		result.append(emitLIMITSTACK(3))
		result.append(emitLIMITLOCAL(1))
		result.append(emitVAR(frame.getNewIndex,"this",ClassType(decl.name.name),0,1)) // CAN USE
//		decl.decl.foreach(
//		    dl => dl match {
//		      case VarDecl(id,t) => result.append(emitVAR(frame.getNewIndex,id.name,t,0,1))
//		      case MethodImpDecl(rt,id,params,body) => {
//		    	  rt match {
//		    	  	case null => {	// constructor
//		    	  		//val expCode = generateStmt(body,decl,sym,true,frame)
//		    	  	}
//		    	  	case _ => println(rt)
//		    	  }
//		      }
//		      case _ =>
//		    }) 
		result.append(emitLABEL(0))
		result.append(emitALOAD(0,frame))
		result.append(emitINVOKESPECIAL(frame))
		for (sym <- in) {
			sym match {
			  	case Symbol(n,ArrayType(size,t),k,_) => {// ARRAYTYPE IN CLASS
			  		val at = sym.typ.asInstanceOf[ArrayType];
			  		result.append(emitALOAD(0,frame))
			  		result.append(emitICONST(at.dimen.value,frame))
			  		if (t == FloatType) result.append(emitWRITESOMESTRING("newarray float"))
			  		else if (t == IntType) result.append(emitWRITESOMESTRING("newarray int"))
			  		else if (t == BoolType) result.append(emitWRITESOMESTRING("newarray boolean"))
			  		else if (t == StringType) result.append(emitWRITESOMESTRING("anewarray java/lang/String"))
			  		else t match {
			  		  case ClassType(nam) => result.append(emitWRITESOMESTRING("anewarray "+nam))
			  		  case _ =>
			  		}
			  		result.append(emitPUTFIELD(sym.name,sym.typ,frame,ClassType(decl.name.name)))
			  	}
			  	case Symbol(n,t,k,Some(num)) => {// CONSTANT DECL IN CLASS
			  		if (t == IntType) {
			  			result.append(emitALOAD(0,frame))
			  			result.append(emitICONST(num.toInt,frame))
			  			result.append(emitPUTFIELD(sym.name,sym.typ,frame,ClassType(decl.name.name)))
			  		} else if (t == FloatType) {
			  			result.append(emitALOAD(0,frame))
			  			result.append(emitFCONST(num,frame))
			  			result.append(emitPUTFIELD(sym.name,sym.typ,frame,ClassType(decl.name.name)))
			  		} else if (t == BoolType) {
			  			result.append(emitALOAD(0,frame))
			  			result.append(emitICONST(num,frame))
			  			result.append(emitPUTFIELD(sym.name,sym.typ,frame,ClassType(decl.name.name)))
			  		} else if (t == StringType) { 
			  			result.append(emitALOAD(0,frame))
			  			result.append(emitLDC("\""+num+"\"",frame))
			  			result.append(emitPUTFIELD(sym.name,sym.typ,frame,ClassType(decl.name.name)))
			  		} else if (t == ClassType(decl.name.name)) {
			  			result.append(emitALOAD(0,frame))
			  			result.append(emitLDC("\""+num+"\"",frame))
			  			result.append(emitPUTFIELD(sym.name,sym.typ,frame,ClassType(decl.name.name)))
			  		} else println(t)
			  	}
			  	case _ => 
			}
		}

		result.append(emitLABEL(1))
		result.append(emitRETURN(null,frame))
		result.append(emitENDMETHOD())
		frame.exitScope();
		result.toString();
	}
	
	def emitINITARRAY(index:Integer, in: Type,frame:Frame) =  {
		var buff = new StringBuffer()
		val at = in.asInstanceOf[ArrayType]
		buff.append(emitICONST(at.dimen.value,frame))
		buff.append(emitNEWARRAY(at.eleType,frame))
		buff.append(emitASTORE(index,frame))
		buff.toString()
	}
	
	def emitLISTARRAY(in:List[Symbol],frame:Frame) = {
		var result = new StringBuffer()
		for (it <- in) {
			//val at = it.typ.asInstanceOf[ArrayType]
			result.append(emitINITARRAY(it.obj.get.toInt,it.typ,frame))
		}
		result.toString();
	}
	
	
}



/**
* 	Frame.scala
*/
import scala.collection.mutable.Stack

class Frame(val isMain:Boolean) {
	
	var currentLabel = 0
	var currOpStackSize = 0
	var maxOpStackSize = 0
	var currIndex = 0
	var maxIndex = 0
	val startLabel = new Stack[Int]()
	val endLabel = new Stack[Int]()
	val indexLocal = new Stack[Int]()
	val conLabel = new Stack[Int]()
	val brkLabel = new Stack[Int]()

	def getCurrIndex(): Int = {
		currIndex
	}



	def setCurrIndex(index: Int) = {
		currIndex = index
	}
	
	/**
	*   return a new label in the method.
	*   @return an integer representing the label.
	*/
	def getNewLabel(): Int = {
		val tmp = currentLabel
		currentLabel += 1;
		tmp
	}
	
	/**
	*	simulate an instruction that pushes a value onto operand stack.
	*/
	def push() {
		currOpStackSize += 1;
		if (maxOpStackSize < currOpStackSize)
			maxOpStackSize = currOpStackSize
	}
	
	/**
	*	simulate an instruction that pops a value out of operand stack.
	*/
	
	def pop() {
		currOpStackSize -= 1;
		if (currOpStackSize < 0)
			throw IllegalRuntimeException("Pop an empty stack(")
	}
	
	/**
	*	return the maximum size of the operand stack that the method needs to use.
	*	@return an integer that represent the maximum stack size
	*/
	def getMaxOpStackSize(): Int = {
		maxOpStackSize
	}
	
	/** 
	*	check if the operand stack is empty or not.
	*	@throws IllegalRuntimeException if the operand stack is not empty.
	*/
	
	def checkOpStack() = {
		if ( currOpStackSize != 0)
			throw  IllegalRuntimeException("Operand Stack is not empty")
	}
	
	/**
	*	invoked when parsing into a new scope inside a method.<p>
	*	This method will create 2 new labels that represent the starting and ending points of the scope.<p>
	*	Then, these labels are pushed onto corresponding stacks.<p>
	*	These labels can be retrieved by getStartLabel() and getEndLabel().<p>
	*	In addition, this method also saves the current index of local variable.
	*/
	
	def enterScope(isProc: Boolean) {
		val start = getNewLabel()
		val end = getNewLabel()
		
		startLabel.push(start)
		endLabel.push(end)
		indexLocal.push(currIndex)
		
		if (isProc) {
			maxOpStackSize = 0
			maxIndex = 0
		}
	}
	
	/**
	* 	invoked when parsing out of a scope in a method.<p>
	* 	This method will pop the starting and ending labels of this scope
	*	and restore the current index
	*/
	
	def exitScope() = {
		if (startLabel.isEmpty || endLabel.isEmpty || indexLocal.isEmpty)
			throw IllegalRuntimeException("Exit scope but startLabel or endLabel or indexLocal is empty")		
		
		startLabel.pop()
		endLabel.pop()
		currIndex = indexLocal.pop
	}
	
	/**
	*	return the starting label of the current scope.
	*	@return an integer representing the starting label
	*/
	
	def getStartLabel(): Int = {
		if (startLabel.isEmpty)
			throw IllegalRuntimeException("Start label is empty ")
			
		startLabel.top
	}
	
	/**
	*	return the ending label of the current scope.
	*	@return an integer representing the ending label
	*/
	
	def getEndLabel(): Int = {
		if (endLabel.isEmpty)
			throw IllegalRuntimeException("End Label is empty ")
			
		endLabel.top
	}
	
	/**
	*	return a new index for a local variable declared in a scope. 
	*	@return an integer that represents the index of the local variable
	*/
	def getNewIndex(): Int = {
		val tmp = currIndex
		
		currIndex += 1;
		if (currIndex > maxIndex)
			maxIndex = currIndex
			
		tmp
	}
	
	/**
	*	return the maximum index used in generating code for the current method
	*	@return an integer representing the maximum index
	*/
	def getMaxIndex(): Int = {
		maxIndex
	}
	
	/**
	*	invoked when parsing into a loop statement.<p>
	*	This method creates 2 new labels that represent the starting and ending label of the loop.<p>
	*	These labels are pushed onto corresponding stacks and are retrieved by getBeginLoopLabel() and getEndLoopLabel().
	*/
	def enterLoop() {
		val con = getNewLabel()
		val brk = getNewLabel()
		conLabel.push(con)
		brkLabel.push(brk)
	}
	
	/**
	*	invoked when parsing out of a loop statement.
	*	This method will take 2 labels representing the starting and ending labels of the current loop out of its stacks.
	*/
	
	def exitLoop() = {
		if (conLabel.isEmpty || brkLabel.isEmpty)
			throw IllegalRuntimeException("Continue or Break label is empty ")
			
		conLabel.pop
		brkLabel.pop
	}
	
	/**
	*	return the label of the innest enclosing loop to which continue statement would jump
	*	@return an integer representing the continue label
	*/
	
	def getContinueLabel(): Int = {
		if (conLabel.isEmpty)
			throw IllegalRuntimeException("Continue label is empty ")
		
		conLabel.top
	}
	
	/**
	*	return the label of the innest enclosing loop to which break statement would jump
	*	@return an integer representing the break label
	*/
	
	def getBreakLabel(): Int = {
		if (brkLabel.isEmpty)
			throw IllegalRuntimeException("Break label is empty ")
			
		brkLabel.top
	}
}


/**
* 	MachineCode.scala
*/

trait MachineCode {
	def emitNULLCONST: String 
	def emitICONST(i: Int): String 
	def emitBIPUSH(i: Int): String 
	def emitSIPUSH(i: Int): String 
	def emitLDC(in: String): String 
	def emitFCONST(in: String): String 
	def emitILOAD(in: Int): String 
	def emitFLOAD(in: Int): String 
	def emitISTORE(in: Int): String 
	def emitFSTORE(in: Int): String 
	def emitALOAD(in: Int): String 
	def emitASTORE(in: Int): String 
	def emitIASTORE(): String 	
	def emitFASTORE(): String 	
	def emitBASTORE(): String 	
	def emitAASTORE(): String 	
	def emitIALOAD(): String 	
	def emitFALOAD(): String 	
	def emitBALOAD(): String 	
	def emitAALOAD(): String 	
	def emitGETSTATIC(lexeme: String, typ: String): String 	
	def emitPUTSTATIC(lexeme: String, typ: String): String 	
	def emitGETFIELD(lexeme: String, typ: String): String 	
	def emitPUTFIELD(lexeme: String, typ: String): String 	
	def emitIADD(): String 	
	def emitFADD(): String 	
	def emitISUB(): String 	
	def emitFSUB(): String 	
	def emitIMUL(): String 	
	def emitFMUL(): String 	
	def emitIDIV(): String 	
	def emitFDIV(): String 	
	def emitIAND(): String 	
	def emitIOR(): String 	
	def emitIREM(): String 	
	def emitIFICMPEQ(label: Int): String 	
	def emitIFICMPNE(label: Int): String 	
	def emitIFICMPLT(label: Int): String 	
	def emitIFICMPLE(label: Int): String  	
	def emitIFICMPGT(label: Int): String  	
	def emitIFICMPGE(label: Int): String  	
	def emitIFEQ(label: Int): String  	
	def emitIFNE(label: Int): String  	
	def emitIFLT(label: Int): String  	
	def emitIFLE(label: Int): String  	
	def emitIFGT(label: Int): String  	
	def emitIFGE(label: Int): String  	
	def emitLABEL(label: Int): String 	
	def emitGOTO(label: Int): String  	
	def emitINEG(): String 	
	def emitFNEG(): String 	
	def emitDUP(): String 
	def emitDUPX2(): String 	
	def emitPOP(): String 	
	def emitI2F(): String 	
	def emitNEW(lexeme: String): String 	
	def emitNEWARRAY(lexeme: String): String 	
	def emitMULTIANEWARRAY (typ: String, dimensions: Int): String 	
	def emitINVOKESTATIC(lexeme: String, typ: String): String 	
	def emitINVOKESPECIAL(lexeme: String, typ: String): String 	
	def emitINVOKESPECIAL(): String 
	def emitINVOKEVIRTUAL(lexeme: String, typ: String): String 	
	def emitIRETURN(): String 
	def emitARETURN(): String 	
	def emitFRETURN(): String 	
	def emitRETURN(): String 	
	def emitLIMITSTACK(in: Int): String 	
	def emitFCMPL(): String 	
	def emitFCMPG(): String 	
	def emitLIMITLOCAL(in: Int): String 	
	def emitVAR(in: Int, varName: String, intyp: String, fromLabel: Int, toLabel: Int): String 	
	def emitVAR(in: Int, varName: String, intyp: String): String 
	def emitMETHOD(lexeme: String, typ: String, isStatic: Boolean): String 
	def emitENDMETHOD(): String 
	def emitSOURCE(lexeme: String): String 	
	def emitCLASS(lexeme: String): String 	
	def emitSUPER(lexeme: String): String 	
	def emitSTATICFIELD(lexeme: String, typ: String, isFinal: Boolean,value:String): String	
	def emitINSTANCEFIELD(lexeme: String, typ: String, isFinal: Boolean,value:String): String 
	def emitWRITESOMESTRING(lexeme: String): String
}

object JasminCode extends MachineCode {
	val END = "\n"
	val INDENT = "\t"
	
	def emitNULLCONST(): String = {		
		INDENT + "aconst_null" + END	
	}
		
	def emitICONST(i: Int): String = {		
		if (i == -1) {
			INDENT + "iconst_m1" + END		
		} else if (i >= 0 && i <= 5){
			INDENT + "iconst_" + i + END					
		} else throw IllegalOperandException
	}
	
	
	def emitBIPUSH(i: Int): String = {
		if ((i >= -128 && i < -1) || (i > 5 && i <= 127))
			INDENT + "bipush " + i + END
		else
			throw IllegalOperandException
	}
	
	
	def emitSIPUSH(i: Int): String = {
		if ((i >= -32768 && i < -128) || (i > 127 && i <= 32767))
			INDENT + "sipush " + i + END
		else
			throw IllegalOperandException
	}
	
	
	def emitLDC(in: String): String = {	
		INDENT + "ldc " + in + END
	}
	
	
	def emitFCONST(in: String): String = {
		if (in.equals("0.0")) {
			INDENT + "fconst_0" + END
		} else if (in.equals("1.0")) {
			INDENT + "fconst_1" + END
		} else if (in.equals("2.0")) {
			INDENT + "fconst_2" + END
		} else{
//			try {
//				in.toFloat
//				emitLDC(in)
//			}catch{
//				case _ => throw IllegalOperandException
//			}	
				in.toFloat
				emitLDC(in)		  
		}
			
	}
	
	
	def emitILOAD(in: Int): String = {
		if (in >= 0 && in <= 3)
			INDENT + "iload_" + in + END
		else
			INDENT + "iload " + in + END
	}
	
	
	def emitFLOAD(in: Int): String = {
		if (in >= 0 && in <= 3)
			INDENT + "fload_" + in + END
		else
			INDENT + "fload " + in + END
	}	
	
	
	def emitISTORE(in: Int): String = {
		if (in >= 0 && in <= 3)
			INDENT + "istore_" + in + END
		else
			INDENT + "istore " + in + END
	}
	
	
	def emitFSTORE(in: Int): String =  {
		if (in >= 0 && in <= 3)
			INDENT + "fstore_" + in + END
		else
			INDENT + "fstore " + in + END
	}

	
	def emitALOAD(in: Int): String = {
		if (in >= 0 && in <= 3)
			INDENT + "aload_" + in + END
		else
			INDENT + "aload " + in + END
	}	

	
	def emitASTORE(in: Int): String = {
		if (in >= 0 && in <= 3)
			INDENT + "astore_" + in + END
		else
			INDENT + "astore " + in + END
	}	

	
	def emitIASTORE(): String = {
		INDENT + "iastore" + END
	}
	
	
	def emitFASTORE(): String = {
		INDENT + "fastore" + END
	}
	
	
	def emitBASTORE(): String = {
		INDENT + "bastore" + END
	}
	
	
	def emitAASTORE(): String = {
		INDENT + "aastore" + END
	}
	
	
	def emitIALOAD(): String = {
		INDENT + "iaload" + END
	}
	
	
	def emitFALOAD(): String = {
		INDENT + "faload" + END
	}
	
	
	def emitBALOAD(): String = {
		INDENT + "baload" + END
	}
	
	
	def emitAALOAD(): String = {
		INDENT + "aaload" + END
	}
	
	
	def emitGETSTATIC(lexeme: String, typ: String): String = {
		INDENT + "getstatic " + lexeme + " " + typ + END
	}
	
	
	def emitPUTSTATIC(lexeme: String, typ: String): String = {
		INDENT + "putfield " + lexeme + " " + typ + END
	}	
	
	
	def emitGETFIELD(lexeme: String, typ: String): String = {
		INDENT + "getfield " + lexeme + " " + typ + END
	}
	
	
	def emitPUTFIELD(lexeme: String, typ: String): String = {
		INDENT + "putfield " + lexeme + " " + typ + END
	}	
	
	
	def emitIADD(): String = {
		INDENT + "iadd" + END
	}
	
	
	def emitFADD(): String = {
		INDENT + "fadd" + END
	}
	
	
	def emitISUB(): String = {
		INDENT + "isub" + END
	}
	
	
	def emitFSUB(): String = {
		INDENT + "fsub" + END
	}
	
	
	def emitIMUL(): String = {
		INDENT + "imul" + END
	}
	
	
	def emitFMUL(): String = {
		INDENT + "fmul" + END
	}
	
	
	def emitIDIV(): String = {
		INDENT + "idiv" + END
	}
	
	
	def emitFDIV(): String = {
		INDENT + "fdiv" + END
	}
	
	
	def emitIAND(): String = {
		INDENT + "iand" + END
	}
	
	
	def emitIOR(): String = {
		INDENT + "ior" + END
	}
	
	
	def emitIREM(): String = {
		INDENT + "irem" + END
	}
	
	
	def emitIFICMPEQ(label: Int): String = {
		INDENT + "if_icmpeq Label" + label + END
	}
	
	
	def emitIFICMPNE(label: Int): String = {
		INDENT + "if_icmpne Label" + label + END
	}
	
	
	def emitIFICMPLT(label: Int): String = {
		INDENT + "if_icmplt Label" + label + END
	}
	
	
	def emitIFICMPLE(label: Int): String  = {
		INDENT + "if_icmple Label" + label + END
	}
	
	
	def emitIFICMPGT(label: Int): String  = {
		INDENT + "if_icmpgt Label" + label + END
	}
	
	
	def emitIFICMPGE(label: Int): String  = {
		INDENT + "if_icmpge Label" + label + END
	}
	
	
	def emitIFEQ(label: Int): String  = {
		INDENT + "ifeq Label" + label + END
	}
	
	
	def emitIFNE(label: Int): String  = {
		INDENT + "ifne Label" + label + END
	}
	
	
	def emitIFLT(label: Int): String  = {
		INDENT + "iflt Label" + label + END
	}
	
	
	def emitIFLE(label: Int): String  = {
		INDENT + "ifle Label" + label + END
	}
	
	
	def emitIFGT(label: Int): String  = {
		INDENT + "ifgt Label" + label + END
	}
	
	
	def emitIFGE(label: Int): String  = {
		INDENT + "ifge Label" + label + END
	}
	
	
	def emitLABEL(label: Int): String = {
		"Label" + label + ":" + END
	}
	
	
	def emitGOTO(label: Int): String  = {
		INDENT + "goto Label" + label + END
	}
	
	
	def emitINEG(): String = {
		INDENT + "ineg" + END
	}
	
	
	def emitFNEG(): String = {
		INDENT + "fneg" + END
	}
	
	
	def emitDUP(): String = {
		INDENT + "dup" + END
	}

	
	def emitDUPX2(): String = {
		INDENT + "dup_x2" + END
	}
	
	
	def emitPOP(): String = {
		INDENT + "pop" + END
	}
	
	
	def emitI2F(): String = {
		INDENT + "i2f" + END
	}
	
	
	def emitNEW(lexeme: String): String = {
		INDENT + "new " + lexeme + END
	}
	
	
	def emitNEWARRAY(lexeme: String): String = {
		INDENT + "newarray " + lexeme + END
	}
	
	
	def emitMULTIANEWARRAY (typ: String, dimensions: Int): String = {
		INDENT + "multianewarray " + typ + " " + dimensions + END
	}
	
	
	def emitINVOKESTATIC(lexeme: String, typ: String): String = {		
		INDENT + "invokestatic " + lexeme + typ + END
	}
	
	def emitINVOKESPECIAL(lexeme: String, typ: String): String = {		
		INDENT + "invokespecial " + lexeme + typ + END
	}
	
	def emitINVOKESPECIAL(): String = {
		INDENT + "invokespecial java/lang/Object/<init>()V" + END
	}
	
	
	def emitINVOKEVIRTUAL(lexeme: String, typ: String): String = {
		INDENT + "invokevirtual " + lexeme + typ + END
	}
	
	
	def emitIRETURN(): String = {
		INDENT + "ireturn" + END
	}
	
	
	def emitARETURN(): String = {
		INDENT + "areturn" + END
	}
	
	
	def emitFRETURN(): String = {
		INDENT + "freturn" + END
	}
	
	
	def emitRETURN(): String = {
		INDENT + "return" + END
	}
	
	def emitLIMITSTACK(in: Int): String = {
		".limit stack " + in + END
	}
	
	def emitFCMPL(): String = {
		INDENT + "fcmpl" + END
	}
	
	def emitFCMPG(): String = {
		INDENT + "fcmpg" + END
	}
	
	def emitLIMITLOCAL(in: Int): String = {
		".limit locals " + in + END
	}
	
	def emitVAR(in: Int, varName: String, intyp: String, fromLabel: Int, toLabel: Int): String = {
		".var " + in + " is " + varName + " " + intyp + " from Label" + fromLabel + " to Label" + toLabel + END
	}

	def emitVAR(in: Int, varName: String, intyp: String): String = {
		".var " + in + " is " + varName + " " + intyp + END
	}
	
	def emitMETHOD(lexeme: String, typ: String, isStatic: Boolean): String = {
		if (isStatic)
			END + ".method public static " + lexeme + typ + END
		else
			END + ".method public " + lexeme + typ + END
	}
	
	def emitENDMETHOD(): String = {
		".end method" + END
	}

	
	def emitSOURCE(lexeme: String): String = {
		".source " + lexeme + END
	}
	
	
	def emitCLASS(lexeme: String): String = {
		".class " + lexeme + END
	}
	
	
	def emitSUPER(lexeme: String): String = {
		".super " + lexeme + END
	}
	
	def emitWRITESOMESTRING(lexeme: String): String = {
		lexeme + END
	}
	
	def emitSTATICFIELD(lexeme: String, typ: String, isFinal: Boolean,value:String): String = {
		val init = if (value.length > 0) " = "+value else ""
		if (isFinal)
			".field static final " + lexeme + " " + typ + init + END
		else
			".field static " + lexeme + " " + typ + init + END	
	}
	
	def emitINSTANCEFIELD(lexeme: String, typ: String, isFinal: Boolean,value:String): String = {
		val init = if (value.length > 0) " = "+value else ""
		if (isFinal)
			".field protected final " + lexeme + " " + typ + init + END
		else
			".field protected " + lexeme + " " + typ + init + END
	}
}
