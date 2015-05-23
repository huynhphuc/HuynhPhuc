#ifndef _HUFFMAN_CODE_H_
#define _HUFFMAN_CODE_H_

#include <iostream>
#include <fstream>
#include <cstring>

#include "Reader.h"
#include "Writer.h"
#include "HCZHeader.h"

using namespace std;

#define ERR -1

#define SUCCESS 0
#define UN_IMPLEMENT -2
#define MAXBUFSIZE 10000

// convert byte to character
#define byte2Char(val) ((unsigned char) ((val) % 256))

// convert character to ASCII
#define char2Int(ch) ((int) (ch) < 0 ? 128 + (((int) (ch)) & 127): (((int) (ch)) & 127))

struct KITU {
	unsigned char 	KyTu;
	unsigned long 	TanSuat;
};

struct TUMA {
	unsigned char 	count;
	char* 			code;
};

struct BOMA {
	KITU 	KiTu;
	TUMA 	TuMa;
};

struct Node {					// Node of tree
	KITU 	Tu;
	Node* 	Trai;
	Node* 	Phai;
	Node () {
		this->Trai 			= NULL;
		this->Phai 			= NULL;
		this->Tu.KyTu 		= '\0';
		this->Tu.TanSuat 	= 0;
	}
};

typedef Node* NODE;

class HangDoi{
public:
    int 	numRoot;
    NODE 	Mang[256];
    HangDoi(){
        	numRoot		=	0;
    }
    void ThemNODE(NODE node)
    {
        int i			=	numRoot - 1;
        while(i >= 0 && Mang[i]->Tu.TanSuat	< node->Tu.TanSuat)
        {
            Mang[i+1]	=	Mang[i--];
            if (i	<	0) 	break;
        }
        Mang[i+1]		=	node;
        numRoot++;
    }
    NODE LayNODE1()
    {
        return 	Mang[--numRoot];
    }
};

//----------------------------------------------------------------------------------------
class HuffmanCode {
public:
    int 	zip (char* inputFile, char* outputFile);
    int 	unzip (char* inputFile, char* outputFile);
    HuffmanCode() {
    }
    void 	DungCayHuffman (KITU arrKitu[256], int i, NODE &Root);
    void 	TaoBangMa (NODE Goc, BOMA BoMa[256], int VT);
private:

};

#endif
