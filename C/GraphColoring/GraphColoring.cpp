#include "defs.h"

class ANode		// Node of adjacency list
{
public:
	char 	aVertex;
	ANode* 	anext;
};

class VNode		// Node of vertex list
{
	public:
	char 	vertex;
	int 	bac;
	ANode* 	aNode;
	VNode* 	link;
};

// class vertex
class vertexList{
public:
	int 	countVertex;
	VNode* 	pHead;
	vertexList();
	~vertexList();
	void addFirst(char newVertex);
	void addLast(char newVertex);
	void insert(VNode* newdata);
	VNode* pop();
	void deleteVertex (char vertex);
};

vertexList::vertexList() {pHead = NULL; countVertex = 0;}
void vertexList::addFirst(char newVertex) {
	VNode* pTemp 	= new VNode;
	pTemp->vertex 	= newVertex;
	pTemp->aNode 	= NULL;
	pTemp->link 	= pHead;
	pHead = pTemp;
	countVertex++;
}
void vertexList::addLast(char newVertex) {
	VNode* pTemp 	  = new VNode;
	VNode* pPre 	  = new VNode;
	if(pHead == NULL){
		pTemp->link   = pHead;
		pTemp->aNode  = NULL;
		pTemp->vertex = newVertex;

		pHead = pTemp;
		countVertex++;
	}
	else{
		pPre = pHead;
		while(pPre->link != NULL){
			pPre 	  = pPre->link;
		}
		pTemp->vertex = newVertex;
		pTemp->link   = pPre->link;
		pPre->link    = pTemp;
		pTemp->aNode  = NULL;
		countVertex++;
	}
	return ;
}
VNode* vertexList::pop() {			// pop first node
	if (pHead == NULL) return NULL;
	if (pHead->link == NULL) {			// only one node
		VNode* pTemp = new VNode;
		pTemp 		 = pHead;
		//delete pHead;
		pHead 		 = NULL;
		return pTemp;
	}
	VNode* pTemp = new VNode;
	pTemp 		 = pHead;
	pHead 		 = pHead->link;
	pTemp->link  = NULL;
	return pTemp;
}
void vertexList::insert(VNode* newdata) {			// insert and sort
	VNode* pCur  = new VNode;
	VNode* pPre  = new VNode;
	if(pHead == NULL) {
		pHead 	 = newdata;
		countVertex++;
	}
	else{
		pPre 	= pHead;			// pointer previous
		pCur 	= pHead->link;		// pointer current
		if (pHead->link == NULL && newdata->bac > pPre->bac) {		// 1 node insert previous
			newdata->link = pHead;
			pHead 		  = newdata;
			countVertex++;
		}
		else if (pHead->link == NULL && newdata->bac < pPre->bac) {	// 1 node insert posterior
			pHead->link   = newdata;
			countVertex++;
		}
		else if (newdata->bac > pPre->bac) {						// exception insert first
			newdata->link = pHead;
			pHead = newdata;
		}
		else {
			while(pCur != NULL && newdata->bac < pCur->bac){		// insert any position
				pCur 	 = pCur->link;
				pPre 	 = pPre->link;
			}
			pPre->link 	 = newdata;
			newdata->link= pCur;
			countVertex++;
		}
	}
	return ;
}
void vertexList::deleteVertex (char vertex) {
	if (pHead == NULL)	return;
	VNode* pPre = pHead;
	VNode* pCur = pHead->link;
	if (pPre->vertex == vertex)	{
		VNode* pTemp = pop();	// should delete node
		ANode* t1    = pTemp->aNode;
		ANode* t2    = pTemp->aNode;
		while (t1){		// delete adjacency vertex
			t2 	     = t1->anext;
			delete t1;
			t1       = t2;
		}
		delete pTemp;
		pTemp = NULL;
	}
	else {
		while (pCur){
			if (pCur->vertex == vertex) {
				pPre->link = pCur->link;
				ANode* t1  = pCur->aNode;
				ANode* t2  = pCur->aNode;
				while (t1){		// delete adjacency vertex
					t2 	   = t1->anext;
					delete t1;
					t1     = t2;
				}
				delete pCur;
				return;
			}
			pCur = pCur->link;
			pPre = pPre->link;
		}
	}
	return;
}
vertexList::~vertexList() {
	VNode* pTemp = pHead;
	ANode* aTemp1;
	ANode* aTemp2;
	while (pTemp!=NULL) {
		pTemp 	 = pTemp->link;
		aTemp1 	 = pHead->aNode;
		while (aTemp1) {
			aTemp2 = aTemp1->anext;
			delete aTemp1;
			aTemp1 = aTemp2;
		}
		delete pHead;
		pHead 	 = pTemp;
	}
}

// class adjacency vertex
class adjacencyList
{
public:
	int 	countAdj;	// count adjacency vertex
	ANode*  ahead;
	adjacencyList();
	~adjacencyList();
	void addLast(char newAdj);
};

adjacencyList::adjacencyList()
{
	ahead		= NULL;
	countAdj	= 0;
}
void adjacencyList::addLast(char newAdj){
	ANode* pTemp 	= new ANode();
	ANode* pPre  	= new ANode();
	if(ahead == NULL){
		pTemp->anext   = ahead;
		pTemp->aVertex = newAdj;
		ahead = pTemp;
		countAdj++;
	}
	else{
		pPre = ahead;
		while(pPre->anext != NULL){
			pPre = pPre->anext;
		}
		pTemp->aVertex = newAdj;
		pTemp->anext   = pPre->anext;
		pPre->anext    = pTemp;
		countAdj++;
	}
	return ;
}
adjacencyList::~adjacencyList() {
	ANode* pTemp = ahead;
	while (pTemp!=NULL) {
		pTemp    = pTemp->anext;
		delete ahead;
		ahead    = pTemp;
	}
}

vertexList* copyGraph (vertexList* graph) {
	vertexList* graphCopy  = new vertexList();
	VNode* headC;
	graphCopy->countVertex = graph->countVertex;
	VNode* headCopy        = graph->pHead;
	adjacencyList* aCopy   = new adjacencyList();
	int testC = 0;
	while(headCopy)		// copy
	{
		graphCopy->addLast(headCopy->vertex);
		if (testC == 0)
			headC = graphCopy->pHead;
		else
			headC = headC->link;
		testC     = 1;
		ANode* aTemp = headCopy->aNode;
		while (aTemp){
			aCopy->addLast(aTemp->aVertex);
			aTemp = aTemp->anext;
		}
		headC->aNode = aCopy->ahead;
		aCopy->ahead = NULL;
		headC->bac   = headCopy->bac;
		headCopy     = headCopy->link;
	}
	return graphCopy;
}

void swapVertex (VNode* t1, VNode* t2) {
	char t = t2->vertex;
	t2->vertex = t1->vertex;
	t1->vertex = t;
	int bt	   = t2->bac;
	t2->bac	   = t1->bac;
	t1->bac    = bt;
	ANode* at  = t2->aNode;
	t2->aNode  = t1->aNode;
	t1->aNode  = at;
}

int GraphColoring(FILE* f, Vertex* &v){
	char ch;
	vertexList* vertex	= new vertexList();
	adjacencyList* ver 	= new adjacencyList();
	VNode* temp;
	int i = 0;

	while(!feof(f))//reading
	{
		ch = fgetc(f);
		if (int(ch) > 64 && int(ch) < 91) {
			if (int(ch) > 64 && int(ch) < 91) {
				vertex->addFirst(ch);		// lay vertex list
				temp = vertex->pHead;
			}
			ch = fgetc(f);
			while (!feof(f)) {

				if (int(ch) > 32 && int(ch) < 91) {
					ver->addLast(ch);		// lay adjacency list
					i++;
					ch = fgetc(f);
					if (feof(f)) {
						temp->aNode = ver->ahead;
						temp->bac	= i;
						ver->ahead	= NULL;
					}
				}
				else if (int(ch) == 32)
					ch = fgetc(f);
				else {
					temp->aNode = ver->ahead;
					temp->bac	= i;
					ver->ahead	= NULL;		// hay
					i = 0;
					break;
				}
			}
		}
	}

	fclose(f);//close file

	//	Create graph
	vertexList* graphTemp = new vertexList();
	while (vertex->pHead) {			// sort bac giam
		graphTemp->insert(vertex->pop());
	}

//	vertexList* graphTemp = copyGraph (graph);
	VNode* 	head   = graphTemp->pHead;
	ANode* 	aHead  = head->aNode;
	VNode* 	t1;
	Vertex* temp1  = NULL;
	Vertex* temp2;
	Vertex* result[30];
	for (int j  = 0; j < 30; j++) result[i] = NULL;

	int 	cor    = 0;
	i = 0;
	//t2 = t1;
	int		soLanTo = 0;


	while (head) {
		while (head) {
			aHead     = graphTemp->pHead->aNode;
			while (aHead) {
				if (head->vertex == aHead->aVertex) break;
				aHead = aHead->anext;
			}
			if (aHead == NULL) {
				t1    = head;
				break;
			}
			head      = head->link;
		}
		if (head == NULL) { //cout<< 9;
			cor++;
			temp2        = temp1;
			temp1        = new Vertex;
			temp1->name  = graphTemp->pHead->vertex;
			temp1->color = cor;
			graphTemp->pop();
		}
		else break;
		head  = graphTemp->pHead;
	}

	Vertex* s = NULL;
	int mauS  = 0;
	if (temp1 != NULL) {
		mauS  = cor;
		s     = temp1;
	}
	//int damage;

	while (head) {
		aHead = graphTemp->pHead->aNode;
		while (aHead) {
			if (head->vertex == aHead->aVertex) break;
			aHead = aHead->anext;
		}
		if (aHead == NULL) {
			swapVertex (t1, head); // Have plus one graph

			// Now paint
			soLanTo++;
			vertexList* graphCp = copyGraph (graphTemp);
			//damage++;
			//-----------------------------
/*			cout<< endl;
			VNode* o = graphCp->pHead;
			while (o) {
				cout<< o->vertex;
				o = o->link;
			}*/
			//-------------------------------
			temp1 = NULL;
			Vertex* temp2;
			ANode* 	test;
			if (mauS)
				cor   	= mauS;
			else cor 	= 0;
			VNode*  dinhCurr  = graphCp->pHead;
			VNode* 	dinhTest  = graphCp->pHead;
			while (dinhCurr) {
				cor++;
				dinhTest  	  = graphCp->pHead;
				// paint head vertex
				temp2 		  = temp1;
				temp1         = new Vertex;
				temp1->name   = dinhCurr->vertex;
				temp1->color  = cor;
				dinhCurr->bac = -1;
				dinhCurr      = dinhCurr->link;
				temp1->next   = temp2;
				// paint vertex no adjacency with this vertex
				while (dinhCurr) {
					while (dinhTest) {		// test vertex
						if (dinhTest->bac == -1) {
						test  = dinhTest->aNode;
						while (test)
							if (dinhCurr->vertex != test->aVertex) {
								test = test->anext;
							}
							else break;
						}
						if (test != NULL) break;
						dinhTest = dinhTest->link;
					}
					if (dinhTest == NULL) {
						temp2         = temp1;
						temp1         = new Vertex;
						temp1->name   = dinhCurr->vertex;
						temp1->color  = cor;
						temp1->next   = temp2;
						dinhCurr->bac = -1;
					}
					dinhTest = graphCp->pHead;
					dinhCurr = dinhCurr->link;
				}

				dinhTest     = graphCp->pHead;
				while (dinhTest) {
					if (dinhTest->bac == -1) {
						graphCp->deleteVertex(dinhTest->vertex);
						dinhTest  = graphCp->pHead;

					}
					else dinhTest = dinhTest->link;
				}
				// prepare new paint
				dinhCurr          = graphCp->pHead;
			}
			graphCp->~vertexList();
			result[i] = temp1;
			result[i]->color = cor;
			i++;
		}
		//if (damage == 4) break;
		head = head->link;
	}


	int 	min  = result[0]->color;
	int 	xuat = 0;
	for (int j   = 0; j < soLanTo; j++) {
		if (result[j]->color < min){
			min  = result[j]->color;
			xuat = j;
		//cout<< result[j]->color;
		}
	}
	//graphCp->~vertexList();
	//graph->~vertexList();
	//v = temp1;

	Vertex* kq  = result[xuat];
	while (kq->next) {
		kq      = kq->next;
	}
	kq->next    = s;

	v = result[xuat];
	return min;
}

