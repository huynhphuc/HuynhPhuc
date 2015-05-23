#ifndef _HUFFMAN_CODE_CPP_
#define _HUFFMAN_CODE_CPP_

#include "HuffmanCode.h"

// convert byte to character
#define byte2Char(val) ((unsigned char) ((val) % 256))

// convert character to ASCII
#define char2Int(ch) ((int) (ch) < 0 ? 128 + (((int) (ch)) & 127): (((int) (ch)) & 127))

typedef Node* NODE;

void HuffmanCode::DungCayHuffman(KITU arrKitu[256], int total, NODE &Root) {
	HangDoi DSNODE;
	while (total) {
	    NODE tmp;								// NODE is node of tree
	    tmp 			= new Node;
	    tmp->Tu.KyTu 	= arrKitu[total - 1].KyTu;
	    tmp->Tu.TanSuat = arrKitu[total - 1].TanSuat;
	    tmp->Trai		= NULL;
	    tmp->Phai		= NULL;
	    DSNODE.ThemNODE(tmp);
	    total--;
	}
    while (DSNODE.numRoot > 1)
	{
        NODE tmp1, tmp2, NodeMoi = new Node;
        tmp1 = DSNODE.LayNODE1();
        tmp2 = DSNODE.LayNODE1();
        NodeMoi->Tu.KyTu 	= '\0';//Node trong mang kt NULL
        NodeMoi->Tu.TanSuat = tmp1->Tu.TanSuat + tmp2->Tu.TanSuat;
        NodeMoi->Trai 		= tmp1;
        NodeMoi->Phai 		= tmp2;
        DSNODE.ThemNODE(NodeMoi);
    }
    Root = DSNODE.LayNODE1();
}

void HuffmanCode::TaoBangMa (NODE Goc, BOMA BoMa[256], int VT)
{
	static int j = 0;	// chi so hang cua tu ma
	static unsigned char Tam[256];
    if (Goc != NULL)
    {
        if (Goc->Phai == NULL && Goc->Trai == NULL)
        {
            BoMa[j].KiTu.KyTu 			= Goc->Tu.KyTu;
            BoMa[j].KiTu.TanSuat 		= Goc->Tu.TanSuat;
            BoMa[j].TuMa.count 			= VT;
            int k = 0;
            BoMa[j].TuMa.code = new char[VT];
            for(int i = 1; i <= VT; i++) {
                BoMa[j].TuMa.code[k]	= Tam[i];
                k++;
            }
            j++;
        }
        VT++;//Duyet nhanh trai
        Tam[VT] = '0';//Lay bit 0
        TaoBangMa(Goc->Trai, BoMa, VT);

        Tam[VT] = '1';//Duyet nhanh phai lay bit 1
        TaoBangMa (Goc->Phai, BoMa, VT);
    }
}

int HuffmanCode::zip (char* inputFile, char* outputFile) {
    // TODO implement for zip
    cout << "zipping...";
    NODE 		Root;
    HuffmanCode a;
	Reader* reader 	= new Reader(inputFile);

	KITU arrKitu[256];
	unsigned char Tu;
	int  tanSo 		= 1, totalChar = 0, j = 0;
	while (reader->isHasNext()) {
		Tu = reader->readByte();
		for (j = 0; j < totalChar; j++) {
			if (arrKitu[j].KyTu == Tu) {
				arrKitu[j].TanSuat++;
				break;
			}
		}
		if (j != totalChar) continue;
		arrKitu[totalChar].KyTu 	= Tu;
		arrKitu[totalChar].TanSuat 	= tanSo;
		totalChar++;
	}
	delete reader;
    DungCayHuffman (arrKitu, totalChar, Root);

    BOMA BoMa[256];
    TaoBangMa(Root, BoMa, 0);

    int 	bodySize = 0;				//----------------------------------------- Xong bodySize
    int 	totalBit = 0;
    for ( int i = 0; i< totalChar; i++){
       	bodySize 	+= BoMa[i].KiTu.TanSuat * BoMa[i].TuMa.count;
       	totalBit 	+= BoMa[i].TuMa.count;
    }
    //cout<< bodySize<< "\t"<< totalChar<< "\t"<< totalBit;
    HCZHeader* header = new HCZHeader;
    header->setBodySize(bodySize);
    header->setTotal(totalChar, totalBit);
    for (int i = 0; i < totalChar; i++) {
    	 header->set(BoMa[i].KiTu.KyTu, BoMa[i].TuMa.count, BoMa[i].TuMa.code);
    }

    Writer* writer 	= new Writer(outputFile);
    header->write(writer);
	Reader* doc 	= new Reader(inputFile);
	while (doc->isHasNext()) {										//---------------------- ghi body
		unsigned char kiTuTam = doc->readByte();
		for (int i 	= 0; i < totalChar; i++){
			if (kiTuTam == BoMa[i].KiTu.KyTu){
				for (int j = 0; j < int(BoMa[i].TuMa.count); j++)
					writer->writeBit(int(BoMa[i].TuMa.code[j]) - 48);
					//writer->writeBit(BoMa[i].TuMa.code[i]);
			}
		}
	}
	delete header;
	delete doc;
    delete writer;
    return UN_IMPLEMENT;
}
//----------------------------unzip-----------------------------------
void dungLaiHuff (NODE &Root, char ch, int nbits, char* code) {
	NODE 	root 	= Root;
	int 	i 		= 0;
	while (nbits)
		if (code[i] == '0') {
				if (root->Trai == NULL) {
					NODE trai 	= new Node();
					root->Trai 	= trai;
				}
				root = root->Trai;
				i++;
				nbits--;
		}
		else if (code[i] == '1') {
				if (root->Phai 	== NULL) {
					NODE phai 	= new Node();
					root->Phai 	= phai;
				}
				root = root->Phai;
				i++;
				nbits--;
		}
	root->Tu.KyTu 	= ch;
}

int HuffmanCode::unzip(char* inputFile, char* outputFile) {
    // TODO implement for unzip
    cout << "unzipping...";
    Reader* 	reader	= new Reader (inputFile);
    HCZHeader* 	header 	= new HCZHeader;
    header->read(reader);
    int 	bodySize 	= header->getBodySize();
    int 	totalChar 	= 0, totalBit = 0;
    int 	nbits 		= 0;
    header->getTotal(totalChar, totalBit);
    char* 	code 		= new char[totalBit];
    NODE 	Root 		= new Node();

    for (int i = -128; i < 128; i++)
    	if (header->get(i, nbits, code) == SUCCESS) {
    		dungLaiHuff(Root,i, nbits, code);
    	}
    Writer* writer = new Writer(outputFile);
    NODE pTemp 	= new Node();
    int i 		= 0;
    while (i < bodySize) {
    	pTemp 	= Root;
    	while (pTemp->Phai != NULL && pTemp->Trai != NULL) {
    		if (reader->readBit() == 0) 	pTemp = pTemp->Trai;
    		else pTemp = pTemp->Phai;
    		i++;
    	}
    		writer->writeByte(pTemp->Tu.KyTu);
    }
    delete code;
    delete Root;
    delete pTemp;
    delete writer;
    delete reader;
    delete header;
    return UN_IMPLEMENT;
}

#endif
