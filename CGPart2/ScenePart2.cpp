// assignment2.cpp : Defines the entry point for the console application.
// Huynh Tan Phuc - 51202787

#include "stdafx.h"

#include <iostream>
#include <windows.h>
#include <math.h>
#include <gl.h>
#include <glut.h>

using namespace std;

#define PI 3.1415926
#define	COLORNUM 14
#define	DEG2RAD (PI/180.0)
#define min(a,b) ((a)<(b)?(a):(b))
#define BUFSIZE 512

float ColorArr[COLORNUM][3] = {{1.0, 0.0, 0.0}, {0.0, 1.0, 0.0}, { 0.0,  0.0, 1.0}, 
							   {1.0, 1.0,  0.0}, { 1.0, 0.0, 1.0},{ 0.0, 1.0, 1.0}, 
							   {0.3, 0.3, 0.3}, {0.5, 0.5, 0.5}, { 0.9,  0.9, 0.9},
							   {1.0, 0.5,  0.5}, { 0.5, 1.0, 0.5},{ 0.5, 0.5, 1.0},
							   {0.0, 0.0, 0.0}, {1.0, 1.0, 1.0}};

bool isRunning = false;
int boundary = 0;

const int screenWidth = 600;
const int screenHeight= 600;
const float goldenRatio = (1 + sqrt(5.0)) / 2; 

bool bWireFrame = false;
bool b4View = false;
bool turnLight1 = true;

const float YPlanePos = 0;

const float	cylBaseRadius = 1.2;
const float	cylBaseHeight = 0.2; 
float cylBaseRotateAngle = 0.0;

const float	cuboidBaseSizeXZ = 0.3; // * 2
const float	cuboidBaseSizeY = 3.0; // *2

const float	cylAxisRadius = 0.1;
const float	cylAxisHeight = 0.3; 
const float	cylAxisOffset = 0.2;
float cylAxisRotateAngle = 0.0;

const float	cuboidAxisXZ = 0.1;
const float	cuboidAxisY = 1.6-2*0.1;

const float	UShapeX = 1.6;
const float	UShapeY = 0.3;
const float	UShapeZ = 1.4;
const float	UShapeThick = 0.1;

const float	cylPlateRadius = 0.7;
const float	cylPlateHeight = 0.1; 
float cylPlateRotateAngle = 0.0;

const float	sphereRadius = 0.17;

const float	sphereShapeRadius = 0.4;
const float	dodecahedronRadius = 0.35;
const float	icosahedronRadius = 0.45;
const float	truncatedCubeSize = 0.4;
const float	truncatedCubeCut = 0.2;

float camera_angle;
float camera_height;
float camera_dis;
float camera_X, camera_Y, camera_Z;
float lookAt_X, lookAt_Y, lookAt_Z;

static GLfloat lightPosition1[4] = {4, 32, 24, 1};
static GLfloat lightPosition2[4] = {-12, 32, -24, 1};

GLfloat	mat_ambient[] = {0.2f, 0.2f, 0.2f, 1.0f};
GLfloat	mat_diffuse[] = {1.0f, 0.0f, 0.0f, 1.0f};
GLfloat	mat_specular[] = {1.0f, 1.0f, 1.0f, 1.0f};
GLfloat mat_shininess = 50.0f;

GLfloat sphereShapeColor[] = {1.0, 0.0, 1.0, 0.5};
GLfloat dodecahedronShapeColor[] = {0.0, 0.0, 1.0, 0.5};
GLfloat icosahedronShapeColor[] = {0.0, 1.0, 0.0, 0.5};
GLfloat truncatedCubeShapeColor[] = {1.0, 1.0, 0.0, 0.5};

GLfloat red_diffuse[] = {1.0, 0.0, 0.0, 0.5};
GLfloat	green_diffuse[] = {0.0, 1.0, 0.0, 0.5};
GLfloat	blue_diffuse[] = {0.0, 0.0, 1.0, 0.5};
GLfloat pink_diffuse[] = {1.0, 0.0, 1.0, 0.5};
GLfloat	yellow_diffuse[] = {1.0, 1.0, 0.0, 0.5};
GLfloat	gray_diffuse[] = {0.5, 0.5, 0.5, 0.5};
GLfloat	peru_diffuse[] = {244 / 255.0, 164 / 255.0, 96 / 255.0, 0.5};
GLfloat	orangered_diffuse[] = {255 / 255.0, 69 / 255.0, 0, 0.5};

void draw(GLint mode);
/*
*	Class supportClass
*/
class Point3
{
public:
	float x, y, z;
	void set(float dx, float dy, float dz)
						{x = dx; y = dy; z = dz;}
	void set(Point3& p)
						{x = p.x; y = p.y; z = p.z;}
	Point3() { x = y = z = 0;}
	Point3(float dx, float dy, float dz)
						{x = dx; y = dy; z = dz;}

};
class Color3
{
public:
	float r, g, b;
	void set(float red, float green, float blue)
						{r = red; g = green; b = blue;}
	void set(Color3& c)
						{r = c.r; g = c.g; b = c.b;}
	Color3() { r = g = b = 0;}
	Color3(float red, float green, float blue)
						{r = red; g = green; b = blue;}

};
class Point2
{
	public:
		Point2() { x = y = 0.0f; } // constructor 1
		Point2(float xx, float yy) { x = xx; y = yy; } // constructor 2
		void set(float xx, float yy) { x = xx; y = yy; }
		float getX() { return x;}
		float getY() { return y;}
		void draw()		{	glBegin(GL_POINTS);
								glVertex2f((GLfloat)x, (GLfloat)y);
							glEnd();
						}
	private:
		float 	x, y;
};
class IntRect
{
	 public:
		IntRect() { l = 0; r = 100; b = 0; t = 100; } // constructor
		IntRect( int left, int right, int bottom, int top)
			{ l = left; r = right; b = bottom; t = top; }
		void set( int left, int right, int bottom, int top)
			{ l = left; r = right; b = bottom; t = top; }
		void draw(){
						glRecti(l, b, r, t);
						glFlush();
					} // draw this rectangle using OpenGL
		int  getWidth(){return (r-l);}
		int  getHeight() { return (t-b);}
	 private:
		int	l, r, b, t;
};
class RealRect
{
	 public:
		RealRect() { l = 0; r = 100; b = 0; t = 100; } // constructor
		RealRect( float left, float right, float bottom, float top)
			{ l = left; r = right; b = bottom; t = top; }
		void set( float left, float right, float bottom, float top)
			{ l = left; r = right; b = bottom; t = top; }
		float  getWidth(){return (r-l);}
		float  getHeight() { return (t-b);}
		void RealRect::draw(){
							glRectf(l, b, r, t);
							glFlush();
						};// draw this rectangle using OpenGL
	 private:
		float	l, r, b, t;
};
class Vector3
{
public:
	float	x, y, z;
	void set(float dx, float dy, float dz)
						{ x = dx; y = dy; z = dz;}
	void set(Vector3& v)
						{ x = v.x; y = v.y; z = v.z;}
	void flip()
						{ x = -x; y = -y; z = -z;}
	//void setDiff(Point3& a, Point3&
	void normalize();
	Vector3() { x = y = z = 0;}
	Vector3(float dx, float dy, float dz)
						{ x = dx; y = dy; z = dz;}
	Vector3(Vector3& v)
						{ x = v.x; y = v.y; z = v.z;}
	Vector3 cross(Vector3 b);
	Vector3 add(Vector3 b);
	Vector3 sub(Vector3 b);
	Vector3 mulC(float c);
	float dot(Vector3 b);
};
//*************************************************************************
//
//  File Name	: Tga.h
//  Author		: NeHe
//  
//*************************************************************************
typedef	struct									
{
	GLubyte	* imageData;									// Image Data (Up To 32 Bits)
	GLuint	bpp;											// Image Color Depth In Bits Per Pixel
	GLuint	width;											// Image Width
	GLuint	height;											// Image Height
	GLuint	texID;											// Texture ID Used To Select A Texture
	GLuint	type;											// Image Type (GL_RGB, GL_RGBA)
} Texture;	
typedef struct
{
	GLubyte Header[12];									// TGA File Header
} TGAHeader;
typedef struct
{
	GLubyte		header[6];								// First 6 Useful Bytes From The Header
	GLuint		bytesPerPixel;							// Holds Number Of Bytes Per Pixel Used In The TGA File
	GLuint		imageSize;								// Used To Store The Image Size When Setting Aside Ram
	GLuint		temp;									// Temporary Variable
	GLuint		type;	
	GLuint		Height;									//Height of Image
	GLuint		Width;									//Width ofImage
	GLuint		Bpp;									// Bits Per Pixel
} TGA;

TGAHeader tgaheader;									// TGA header
TGA tga;										// TGA image data

GLubyte uTGAcompare[12] = {0,0,2, 0,0,0,0,0,0,0,0,0};	// Uncompressed TGA Header

bool LoadTGA(Texture * texture, char * filename)				// Load a TGA file
{
	FILE * fTGA;												// File pointer to texture file
	fTGA = fopen(filename, "rb");								// Open file for reading

	if(fTGA == NULL)											// If it didn't open....
	{
		return false;														// Exit function
	}

	if(fread(&tgaheader, sizeof(TGAHeader), 1, fTGA) == 0)					// Attempt to read 12 byte header from file
	{
		if(fTGA != NULL)													// Check to seeiffile is still open
		{
			fclose(fTGA);													// If it is, close it
		}
		return false;														// Exit function
	}

																	// an Uncompressed TGA image
	if(fread(tga.header, sizeof(tga.header), 1, fTGA) == 0)					// Read TGA header
	{										
		if(fTGA != NULL)													// if file is still open
		{
			fclose(fTGA);													// Close it
		}
		return false;														// Return failular
	}	

	texture->width  = tga.header[1] * 256 + tga.header[0];					// Determine The TGA Width	(highbyte*256+lowbyte)
	texture->height = tga.header[3] * 256 + tga.header[2];					// Determine The TGA Height	(highbyte*256+lowbyte)
	texture->bpp	= tga.header[4];										// Determine the bits per pixel
	tga.Width		= texture->width;										// Copy width into local structure						
	tga.Height		= texture->height;										// Copy height into local structure
	tga.Bpp			= texture->bpp;											// Copy BPP into local structure

	if((texture->width <= 0) || (texture->height <= 0) || ((texture->bpp != 24) && (texture->bpp !=32)))	// Make sure all information is valid
	{
		if(fTGA != NULL)													// Check if file is still open
		{
			fclose(fTGA);													// If so, close it
		}
		return false;														// Return failed
	}

	if(texture->bpp == 24)													// If the BPP of the image is 24...
		texture->type	= GL_RGB;											// Set Image type to GL_RGB
	else																	// Else if its 32 BPP
		texture->type	= GL_RGBA;											// Set image type to GL_RGBA

	tga.bytesPerPixel	= (tga.Bpp / 8);									// Compute the number of BYTES per pixel
	tga.imageSize		= (tga.bytesPerPixel * tga.Width * tga.Height);		// Compute the total amout ofmemory needed to store data
	texture->imageData	= (GLubyte *)malloc(tga.imageSize);					// Allocate that much memory

	if(texture->imageData == NULL)											// If no space was allocated
	{
		fclose(fTGA);														// Close the file
		return false;														// Return failed
	}

	if(fread(texture->imageData, 1, tga.imageSize, fTGA) != tga.imageSize)	// Attempt to read image data
	{
		if(texture->imageData != NULL)										// If imagedata has data in it
		{
			free(texture->imageData);										// Delete data from memory
		}
		fclose(fTGA);														// Close file
		return false;														// Return failed
	}

	// switch R and B
	for (int i = 0; i < tga.imageSize; i += tga.bytesPerPixel)
	{
		GLubyte temp = texture->imageData[i];
		texture->imageData[i] = texture->imageData[i+2];
		texture->imageData[i+2] = temp;
	}
	
		
	fclose(fTGA);															// Close file
	return true;															// All went well, continue on
}

Texture _floorTex;

void loadTextures(void)	{
    bool status = LoadTGA(&_floorTex, "marble.tga");
    if(status) {
	glGenTextures(1, &_floorTex.texID);
    glBindTexture(GL_TEXTURE_2D, _floorTex.texID);

	glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER, GL_LINEAR);

	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, _floorTex.width, _floorTex.height , 0, GL_RGB, GL_UNSIGNED_BYTE, _floorTex.imageData);

	if(_floorTex.imageData) free(_floorTex.imageData);
    }
}

/*
*	Class Mesh
*/
class VertexID {
public:
	int	_vertIndex;
	int	_colorIndex;
};
class Face {
public:
	int	_nVerts;
	VertexID* _vert;
	Vector3	_facenorm;
	
	Face() {
		_nVerts	= 0;
		_vert	= NULL;
	}
	~Face() {
		if(_vert != NULL) {
			delete[] _vert;
			_vert = NULL;
		}
		_nVerts = 0;
	}
};
//tich co huong cua vecto hien taj voi vecto b
Vector3 Vector3::cross(Vector3 b)
{
	Vector3 c(y*b.z-z*b.y, z*b.x-x*b.z, x*b.y-y*b.x);
	return c;
}

Vector3 Vector3::add(Vector3 b) {
	Vector3 c(x + b.x, y + b.y, z + b.z);
	return c;
}

Vector3 Vector3::sub(Vector3 b) {
	Vector3 c(x - b.x, y - b.y, z - b.z);
	return c;
}

Vector3 Vector3::mulC(float c) {
	Vector3 p(c * x, c * y, c * z);
	return p;
}

Vector3 _S0(6, 6, 6);
Vector3 _A(0, 0, 0);
Vector3 _n(0, 1, 0);

Vector3 Vshadow(Vector3& S, Vector3& A, Vector3& V, Vector3& n) {
	Vector3 ASubS(A.sub(S));
	Vector3 VSubS(V.sub(S));
	float nDotASubS = n.dot(ASubS);
	float nDotVSubS = n.dot(VSubS);
	Vector3 t(VSubS.mulC(nDotASubS / nDotVSubS));

	return S.add(t);
}

//tinh tich vo huong cua 2 vec to, vecto hien taj voi vecto b
float Vector3::dot(Vector3 b)
{
	return x*b.x + y*b.y + z*b.z;
}

//chuyen ve vecto don vi
void Vector3::normalize()
{
	float temp = sqrt(x*x + y*y + z*z);
	x = x/temp;
	y = y/temp;
	z = z/temp;
}
class Mesh {
public:
	float _slideX, _slideY, _slideZ;
	float _rotateX, _rotateY, _rotateZ;

	int	_numVerts;
	Point3*	_pt;
	
	int	_numFaces;
	Face* _face;
public:
	Mesh() {
		_slideX = 0;
		_slideY = 0;
		_slideZ = 0;
		_rotateX = 0;
		_rotateY = 0;
		_rotateZ = 0;
		_numVerts = 0;
		_pt	= NULL;
		_numFaces = 0;
		_face = NULL;
	}
	~Mesh() {
		if (_pt != NULL) {
			delete[] _pt;
		}	
		if(_face != NULL) {
			delete[] _face;
		}
		_numVerts = 0;
		_numFaces = 0;
	}
	void DrawWireframe();
	void DrawColor();
	void DrawShadowColor();
	void SetColor(int colorIdx);
	void CreateTetrahedron();
	void CreateCube(float fSize);
	void CreateCuboid(float fLength, float fWidth, float fHeight);
	void CreateCylinder(int nSegment, float fHeight, float fRadius);
	void CreateSphere(int nStacks, int nSlices, float fRadius);
	void CreateUShape(float	fSizeX, float fSizeY, float	fSizeZ, float fThick);
	void DrawPentafaceVertex(int aNumOfFace, int aVer1, int aVer2, int aVer3, int aVer4, int aVer5);
	void CreateDodecahedron(float fRadius);
	void DrawTrifaceVertex(int anIndexFace, int aVer1, int aVer2, int aVer3);
	void CreateIcosahedron(float fRadius);
	void DrawOctafaceVertex(int aNumOfFace, int aVer1, int aVer2, int aVer3, int aVer4, int aVer5, int aVer6, int aVer7, int aVer8);
	void CreateTruncatedCube(float fSize, float fCut);
	void CreateShadowTruncatedCube(float fSize, float fCut);
	void CalculateFacesNorm();
	void Draw();
};
///////////////////////////////////
void Mesh::CalculateFacesNorm() {
	float mX, mY, mZ;
	for(int i = 0; i < _numFaces; i++){
		mX = 0;
		mY = 0;
		mZ = 0;
		for( int j = 0; j < _face[i]._nVerts; j++){
			// mX += (_pt[_face[i]._vert[j]._vertIndex].y - _pt[_face[i]._vert[(j + 1) % _face[i]._nVerts]._vertIndex].y) * (_pt[_face[i]._vert[j]._vertIndex].z + _pt[_face[i]._vert[(j + 1) % _face[i]._nVerts]._vertIndex].z);	
			// mY += (_pt[_face[i]._vert[j]._vertIndex].z - _pt[_face[i]._vert[(j + 1) % _face[i]._nVerts]._vertIndex].z) * (_pt[_face[i]._vert[j]._vertIndex].x + _pt[_face[i]._vert[(j + 1) % _face[i]._nVerts]._vertIndex].x);
			// mZ += (_pt[_face[i]._vert[j]._vertIndex].x - _pt[_face[i]._vert[(j + 1) % _face[i]._nVerts]._vertIndex].x) * (_pt[_face[i]._vert[j]._vertIndex].y + _pt[_face[i]._vert[(j + 1) % _face[i]._nVerts]._vertIndex].y);
		
			if (j + 1 == _face[i]._nVerts) {
				mX += (_pt[_face[i]._vert[j]._vertIndex].y - _pt[_face[i]._vert[0]._vertIndex].y) * (_pt[_face[i]._vert[j]._vertIndex].z + _pt[_face[i]._vert[0]._vertIndex].z);	
				mY += (_pt[_face[i]._vert[j]._vertIndex].z - _pt[_face[i]._vert[0]._vertIndex].z) * (_pt[_face[i]._vert[j]._vertIndex].x + _pt[_face[i]._vert[0]._vertIndex].x);
				mZ += (_pt[_face[i]._vert[j]._vertIndex].x - _pt[_face[i]._vert[0]._vertIndex].x) * (_pt[_face[i]._vert[j]._vertIndex].y + _pt[_face[i]._vert[0]._vertIndex].y);
			}
			else {
				mX += (_pt[_face[i]._vert[j]._vertIndex].y - _pt[_face[i]._vert[j + 1]._vertIndex].y) * (_pt[_face[i]._vert[j]._vertIndex].z + _pt[_face[i]._vert[j + 1]._vertIndex].z);	
				mY += (_pt[_face[i]._vert[j]._vertIndex].z - _pt[_face[i]._vert[j + 1]._vertIndex].z) * (_pt[_face[i]._vert[j]._vertIndex].x + _pt[_face[i]._vert[j + 1]._vertIndex].x);
				mZ += (_pt[_face[i]._vert[j]._vertIndex].x - _pt[_face[i]._vert[j + 1]._vertIndex].x) * (_pt[_face[i]._vert[j]._vertIndex].y + _pt[_face[i]._vert[j + 1]._vertIndex].y);
			}
		}
		_face[i]._facenorm.set(mX, mY, mZ);
		_face[i]._facenorm.normalize();
	}

}
void Mesh::Draw() {
	for (int f = 0; f < _numFaces; f++) {
		glBegin(GL_POLYGON);
		for (int v = 0; v < _face[f]._nVerts; v++) {
			int iv = _face[f]._vert[v]._vertIndex;
			glNormal3f(_face[f]._facenorm.x, _face[f]._facenorm.y, _face[f]._facenorm.z);
			glVertex3f(_pt[iv].x, _pt[iv].y, _pt[iv].z);
		}
		glEnd();
	}
}
void Mesh::SetColor(int colorIdx) {
	for (int f = 0; f < _numFaces; f++) {
		for (int v = 0; v < _face[f]._nVerts; v++) {
			_face[f]._vert[v]._colorIndex = colorIdx;
		}
	}
}
void Mesh::CreateCube(float	fSize) {
	int i;
	_numVerts=8;
	_pt = new Point3[_numVerts];
	_pt[0].set(-fSize, fSize, fSize);
	_pt[1].set( fSize, fSize, fSize);
	_pt[2].set( fSize, fSize, -fSize);
	_pt[3].set(-fSize, fSize, -fSize);
	_pt[4].set(-fSize, -fSize, fSize);
	_pt[5].set( fSize, -fSize, fSize);
	_pt[6].set( fSize, -fSize, -fSize);
	_pt[7].set(-fSize, -fSize, -fSize);

	_numFaces= 6;
	_face = new Face[_numFaces];

	//Left _face
	_face[0]._nVerts = 4;
	_face[0]._vert = new VertexID[_face[0]._nVerts];
	_face[0]._vert[0]._vertIndex = 1;
	_face[0]._vert[1]._vertIndex = 5;
	_face[0]._vert[2]._vertIndex = 6;
	_face[0]._vert[3]._vertIndex = 2;
	for(i = 0; i < _face[0]._nVerts ; i++)
		_face[0]._vert[i]._colorIndex = 0;
	
	//Right _face
	_face[1]._nVerts = 4;
	_face[1]._vert = new VertexID[_face[1]._nVerts];
	_face[1]._vert[0]._vertIndex = 0;
	_face[1]._vert[1]._vertIndex = 3;
	_face[1]._vert[2]._vertIndex = 7;
	_face[1]._vert[3]._vertIndex = 4;
	for(i = 0; i < _face[1]._nVerts ; i++)
		_face[1]._vert[i]._colorIndex = 1;

	//top _face
	_face[2]._nVerts = 4;
	_face[2]._vert = new VertexID[_face[2]._nVerts];
	_face[2]._vert[0]._vertIndex = 0;
	_face[2]._vert[1]._vertIndex = 1;
	_face[2]._vert[2]._vertIndex = 2;
	_face[2]._vert[3]._vertIndex = 3;
	for(i = 0; i < _face[2]._nVerts ; i++)
		_face[2]._vert[i]._colorIndex = 2;

	//bottom _face
	_face[3]._nVerts = 4;
	_face[3]._vert = new VertexID[_face[3]._nVerts];
	_face[3]._vert[0]._vertIndex = 7;
	_face[3]._vert[1]._vertIndex = 6;
	_face[3]._vert[2]._vertIndex = 5;
	_face[3]._vert[3]._vertIndex = 4;
	for(i = 0; i < _face[3]._nVerts ; i++)
		_face[3]._vert[i]._colorIndex = 3;

	//near _face
	_face[4]._nVerts = 4;
	_face[4]._vert = new VertexID[_face[4]._nVerts];
	_face[4]._vert[0]._vertIndex = 4;
	_face[4]._vert[1]._vertIndex = 5;
	_face[4]._vert[2]._vertIndex = 1;
	_face[4]._vert[3]._vertIndex = 0;
	for(i = 0; i < _face[4]._nVerts ; i++)
		_face[4]._vert[i]._colorIndex = 4;

	//Far _face
	_face[5]._nVerts = 4;
	_face[5]._vert = new VertexID[_face[5]._nVerts];
	_face[5]._vert[0]._vertIndex = 3;
	_face[5]._vert[1]._vertIndex = 2;
	_face[5]._vert[2]._vertIndex = 6;
	_face[5]._vert[3]._vertIndex = 7;
	for(i = 0; i < _face[5]._nVerts ; i++)
		_face[5]._vert[i]._colorIndex = 5;

}
void Mesh::CreateCylinder(int nSegment, float fHeight, float fRadius) {
	int j = 2;
	float fi = 2 * PI / nSegment, x, y, z;
	_numVerts=nSegment * 2 + 2;
	_pt = new Point3[_numVerts];
	y = fHeight / 2;
	_pt[0].set(0, -y, 0);
	_pt[1].set(0, y, 0);
	for (int i = 0; i < nSegment; i ++) {
		x = fRadius * cos(i * fi);
		z = fRadius * sin(i * fi);
		_pt[j].set(x, -y, z); j++;
		_pt[j].set(x, y, z); j++;
	}

	_numFaces= nSegment * 3;
	_face = new Face[_numFaces];

	int k = 0;
	for (int i = 0; i < nSegment * 2; i += 2) {
		_face[k]._nVerts = 3;
		_face[k]._vert = new VertexID[_face[k]._nVerts];
		_face[k]._vert[0]._vertIndex = 2 + i;
		if (2 + i == nSegment * 2) _face[k]._vert[1]._vertIndex = 2;
		else _face[k]._vert[1]._vertIndex = 4 + i;
		_face[k]._vert[2]._vertIndex = 0;

		for(int j = 0; j < _face[k]._nVerts ; j++)
			_face[k]._vert[j]._colorIndex = 0;
		k++;
	}
	
	for (int i = 0; i < nSegment * 2; i += 2){
		_face[k]._nVerts = 3;
		_face[k]._vert = new VertexID[_face[k]._nVerts];
		_face[k]._vert[0]._vertIndex = 1;
		if (3 + i > nSegment * 2) _face[k]._vert[1]._vertIndex = 3;
		else _face[k]._vert[1]._vertIndex = 5 + i;
		_face[k]._vert[2]._vertIndex = 3 + i;

		for(int j = 0; j < _face[i]._nVerts ; j++)
			_face[i]._vert[j]._colorIndex = 1;
		k++;
	}
	
	for (int i = 0; i < nSegment * 2; i += 2){
		_face[k]._nVerts = 4;
		_face[k]._vert = new VertexID[_face[k]._nVerts];
		if (2 + i == nSegment * 2) _face[k]._vert[0]._vertIndex = 2;
		else _face[k]._vert[0]._vertIndex = 4 + i;
		_face[k]._vert[1]._vertIndex = 2 + i;
		_face[k]._vert[2]._vertIndex = 3 + i;
		if (2 + i == nSegment * 2) _face[k]._vert[3]._vertIndex = 3;
		else _face[k]._vert[3]._vertIndex = 5 + i;

		for(int j = 0; j < _face[k]._nVerts ; j++)
			_face[k]._vert[j]._colorIndex = 2;

		k++;
	}
}
void Mesh::CreateSphere(int nStacks, int nSlices, float fRadius) {
	_numVerts=(nSlices + 1) * (nStacks + 1) * 2;
	_pt = new Point3[_numVerts];
	int k = 0;
	float theta = 0.0, phi = 0.0, nextTheta = 0.0, x, y, z;
	
	for(int i = 0; i <= nSlices; i++) {
		theta = i * (2 * PI / nSlices);
		nextTheta = ( i + 1) * ( 2 * PI / nSlices);	

		for(int j = 0; j <= nStacks; j++) {
			phi = PI/2 - j * (PI / nStacks);
			x = fRadius * sin(theta) * cos(phi);
			y = fRadius * sin(phi);
			z = fRadius * cos(theta) * cos(phi);
			_pt[k].set(x, z, y); k++;
			x = fRadius * sin(nextTheta) * cos(phi);
			y = fRadius * sin(phi);
			z = fRadius * cos(nextTheta) * cos(phi);
			_pt[k].set(x, z, y); k++;
		}
	}

	_numFaces = nSlices * (nStacks + 1);
	_face = new Face[_numFaces];
	for (int i = 0; i < _numFaces; i++) {
		_face[i]._nVerts = 4;
		_face[i]._vert = new VertexID[_face[i]._nVerts];
		_face[i]._vert[0]._vertIndex = 0 + 2 * i;
		_face[i]._vert[1]._vertIndex = 1 + 2 * i;
		_face[i]._vert[2]._vertIndex = 3 + 2 * i;
		_face[i]._vert[3]._vertIndex = 2 + 2 * i;

		for(int j = 0; j < _face[i]._nVerts ; j++) _face[i]._vert[j]._colorIndex = i;
	}

}
void Mesh::CreateUShape(float fSizeX, float fSizeY, float fSizeZ, float fThick){
	_numVerts = 20;
	_pt = new Point3[_numVerts];
	_pt[0].set(fSizeX, -fSizeY, fSizeZ);
	_pt[1].set(fSizeX, -fSizeY, -fSizeZ);
	_pt[2].set(fSizeX, fSizeY, -fSizeZ);
	_pt[3].set(fSizeX, fSizeY, fSizeZ);
	_pt[4].set(fSizeX-fThick, fSizeY, fSizeZ);
	_pt[5].set(fSizeX-fThick, fSizeY, -fSizeZ);
	_pt[6].set(fSizeX-fThick, -fSizeY, -fSizeZ);
	_pt[7].set(fSizeX-fThick, -fSizeY, fSizeZ);
	_pt[8].set(-fSizeX+fThick, -fSizeY, fSizeZ);
	_pt[10].set(-fSizeX+fThick, fSizeY, -fSizeZ);
	_pt[9].set(-fSizeX+fThick, -fSizeY, -fSizeZ);
	_pt[11].set(-fSizeX+fThick, fSizeY, fSizeZ);
	_pt[12].set(-fSizeX, fSizeY, fSizeZ);
	_pt[13].set(-fSizeX, fSizeY, -fSizeZ);
	_pt[14].set(-fSizeX, -fSizeY, -fSizeZ);
	_pt[15].set(-fSizeX, -fSizeY, fSizeZ);
	_pt[17].set(fSizeX, fSizeY, -fSizeZ-fThick);
	_pt[18].set(fSizeX, -fSizeY, -fSizeZ-fThick);
	_pt[16].set(-fSizeX, fSizeY, -fSizeZ-fThick);
	_pt[19].set(-fSizeX, -fSizeY, -fSizeZ-fThick);

	_numFaces= 18;
	_face = new Face[_numFaces];

	int k = 0;
	for(int j = 0; j < 2; j++){		// For 1, draw front cuboid, draw back cuboid	
		_face[k]._nVerts = 4;
		_face[k]._vert = new VertexID[_face[k]._nVerts];
		_face[k]._vert[0]._vertIndex = 0+8*j;
		_face[k]._vert[1]._vertIndex = 1+8*j;
		_face[k]._vert[2]._vertIndex = 2+8*j;
		_face[k]._vert[3]._vertIndex = 3+8*j;
		for(int i = 0; i<_face[0]._nVerts ; i++)
			_face[k]._vert[i]._colorIndex = 0;
		k++;
		_face[k]._nVerts = 4;
		_face[k]._vert = new VertexID[_face[k]._nVerts];
		_face[k]._vert[0]._vertIndex = 4+8*j;
		_face[k]._vert[1]._vertIndex = 5+8*j;
		_face[k]._vert[2]._vertIndex = 6+8*j;
		_face[k]._vert[3]._vertIndex = 7+8*j;
		for(int i = 0; i<_face[k]._nVerts ; i++)
			_face[k]._vert[i]._colorIndex = 0;
		k++;
		_face[k]._nVerts = 4;
		_face[k]._vert = new VertexID[_face[k]._nVerts];
		_face[k]._vert[0]._vertIndex = 3+8*j;
		_face[k]._vert[1]._vertIndex = 2+8*j;
		_face[k]._vert[2]._vertIndex = 5+8*j;
		_face[k]._vert[3]._vertIndex = 4+8*j;
		for(int i = 0; i<_face[k]._nVerts ; i++)
			_face[k]._vert[i]._colorIndex = 0;
		k++;
		_face[k]._nVerts = 4;
		_face[k]._vert = new VertexID[_face[k]._nVerts];
		_face[k]._vert[0]._vertIndex = 7+8*j;
		_face[k]._vert[1]._vertIndex = 6+8*j;
		_face[k]._vert[2]._vertIndex = 1+8*j;
		_face[k]._vert[3]._vertIndex = 0+8*j;
		for(int i = 0; i<_face[k]._nVerts ; i++)
			_face[k]._vert[i]._colorIndex = 0;
		k++;
		_face[k]._nVerts = 4;
		_face[k]._vert = new VertexID[_face[4]._nVerts];
		_face[k]._vert[0]._vertIndex = 0+8*j;
		_face[k]._vert[1]._vertIndex = 3+8*j;
		_face[k]._vert[2]._vertIndex = 4+8*j;
		_face[k]._vert[3]._vertIndex = 7+8*j;
		for(int i = 0; i<_face[k]._nVerts ; i++)
			_face[k]._vert[i]._colorIndex = 0;
		k++;
		_face[k]._nVerts = 4;
		_face[k]._vert = new VertexID[_face[k]._nVerts];
		_face[k]._vert[0]._vertIndex = 6;
		_face[k]._vert[1]._vertIndex = 5;
		_face[k]._vert[2]._vertIndex = 2;
		_face[k]._vert[3]._vertIndex = 1;
		for(int i = 0; i<_face[k]._nVerts ; i++)
			_face[k]._vert[i]._colorIndex = 0;
		k++;
	}

	// Draw cuboid behind 0x
	_face[k]._nVerts = 4;
	_face[k]._vert = new VertexID[_face[k]._nVerts];
	_face[k]._vert[0]._vertIndex = 1;
	_face[k]._vert[1]._vertIndex = 18;
	_face[k]._vert[2]._vertIndex = 17;
	_face[k]._vert[3]._vertIndex = 2;
	for(int i = 0; i<_face[0]._nVerts ; i++)
		_face[k]._vert[i]._colorIndex = 0;
	k++;
	_face[k]._nVerts = 4;
	_face[k]._vert = new VertexID[_face[k]._nVerts];
	_face[k]._vert[0]._vertIndex = 13;
	_face[k]._vert[1]._vertIndex = 16;
	_face[k]._vert[2]._vertIndex = 19;
	_face[k]._vert[3]._vertIndex = 14;
	for(int i = 0; i<_face[k]._nVerts ; i++)
		_face[k]._vert[i]._colorIndex = 1;
	k++;
	_face[k]._nVerts = 4;
	_face[k]._vert = new VertexID[_face[4]._nVerts];
	_face[k]._vert[0]._vertIndex = 2;
	_face[k]._vert[1]._vertIndex = 17;
	_face[k]._vert[2]._vertIndex = 16;
	_face[k]._vert[3]._vertIndex = 13;
	for(int i = 0; i<_face[k]._nVerts ; i++)
		_face[k]._vert[i]._colorIndex = 4;
	k++;
	_face[k]._nVerts = 4;
	_face[k]._vert = new VertexID[_face[k]._nVerts];
	_face[k]._vert[0]._vertIndex = 14;
	_face[k]._vert[1]._vertIndex = 19;
	_face[k]._vert[2]._vertIndex = 18;
	_face[k]._vert[3]._vertIndex = 1;
	for(int i = 0; i<_face[k]._nVerts ; i++)
		_face[k]._vert[i]._colorIndex = 5;
	k++;
	_face[k]._nVerts = 4;
	_face[k]._vert = new VertexID[_face[k]._nVerts];
	_face[k]._vert[0]._vertIndex = 16;
	_face[k]._vert[1]._vertIndex = 17;
	_face[k]._vert[2]._vertIndex = 18;
	_face[k]._vert[3]._vertIndex = 19;
	for(int i = 0; i<_face[k]._nVerts ; i++)
		_face[k]._vert[i]._colorIndex = 2;
	k++;
	_face[k]._nVerts = 4;
	_face[k]._vert = new VertexID[_face[k]._nVerts];
	_face[k]._vert[0]._vertIndex = 1;
	_face[k]._vert[1]._vertIndex = 2;
	_face[k]._vert[2]._vertIndex = 13;
	_face[k]._vert[3]._vertIndex = 14;
	for(int i = 0; i<_face[k]._nVerts ; i++)
		_face[k]._vert[i]._colorIndex = 3;

}
void Mesh::CreateCuboid(float fLength, float fWidth, float fHeight) {
	int i;

	_numVerts=8;
	_pt = new Point3[_numVerts];
	_pt[0].set(-fLength, fWidth, fHeight);
	_pt[1].set( fLength, fWidth, fHeight);
	_pt[2].set( fLength, fWidth, -fHeight);
	_pt[3].set(-fLength, fWidth, -fHeight);
	_pt[4].set(-fLength, -fWidth, fHeight);
	_pt[5].set( fLength, -fWidth, fHeight);
	_pt[6].set( fLength, -fWidth, -fHeight);
	_pt[7].set(-fLength, -fWidth, -fHeight);


	_numFaces= 6;
	_face = new Face[_numFaces];

	//Right _face
	_face[0]._nVerts = 4;
	_face[0]._vert = new VertexID[_face[0]._nVerts];
	_face[0]._vert[0]._vertIndex = 1;
	_face[0]._vert[1]._vertIndex = 5;
	_face[0]._vert[2]._vertIndex = 6;
	_face[0]._vert[3]._vertIndex = 2;
	for(i = 0; i<_face[0]._nVerts ; i++)
		_face[0]._vert[i]._colorIndex = 0;
	
	//Left _face
	_face[1]._nVerts = 4;
	_face[1]._vert = new VertexID[_face[1]._nVerts];
	_face[1]._vert[0]._vertIndex = 0;
	_face[1]._vert[1]._vertIndex = 3;
	_face[1]._vert[2]._vertIndex = 7;
	_face[1]._vert[3]._vertIndex = 4;
	for(i = 0; i<_face[1]._nVerts ; i++)
		_face[1]._vert[i]._colorIndex = 1;

	//Top _face
	_face[2]._nVerts = 4;
	_face[2]._vert = new VertexID[_face[2]._nVerts];
	_face[2]._vert[0]._vertIndex = 0;
	_face[2]._vert[1]._vertIndex = 1;
	_face[2]._vert[2]._vertIndex = 2;
	_face[2]._vert[3]._vertIndex = 3;
	for(i = 0; i<_face[2]._nVerts ; i++)
		_face[2]._vert[i]._colorIndex = 2;

	//bottom _face
	_face[3]._nVerts = 4;
	_face[3]._vert = new VertexID[_face[3]._nVerts];
	_face[3]._vert[0]._vertIndex = 7;
	_face[3]._vert[1]._vertIndex = 6;
	_face[3]._vert[2]._vertIndex = 5;
	_face[3]._vert[3]._vertIndex = 4;
	for(i = 0; i<_face[3]._nVerts ; i++)
		_face[3]._vert[i]._colorIndex = 3;

	//near _face
	_face[4]._nVerts = 4;
	_face[4]._vert = new VertexID[_face[4]._nVerts];
	_face[4]._vert[0]._vertIndex = 4;
	_face[4]._vert[1]._vertIndex = 5;
	_face[4]._vert[2]._vertIndex = 1;
	_face[4]._vert[3]._vertIndex = 0;
	for(i = 0; i<_face[4]._nVerts ; i++)
		_face[4]._vert[i]._colorIndex = 4;

	//Far _face
	_face[5]._nVerts = 4;
	_face[5]._vert = new VertexID[_face[5]._nVerts];
	_face[5]._vert[0]._vertIndex = 3;
	_face[5]._vert[1]._vertIndex = 2;
	_face[5]._vert[2]._vertIndex = 6;
	_face[5]._vert[3]._vertIndex = 7;
	for(i = 0; i<_face[5]._nVerts ; i++)
		_face[5]._vert[i]._colorIndex = 5;

}
void Mesh::CreateTetrahedron() {
	int i;
	_numVerts=4;
	_pt = new Point3[_numVerts];
	_pt[0].set(0, 0, 0);
	_pt[1].set(1, 0, 0);
	_pt[2].set(0, 1, 0);
	_pt[3].set(0, 0, 1);

	_numFaces= 4;
	_face = new Face[_numFaces];

	_face[0]._nVerts = 3;
	_face[0]._vert = new VertexID[_face[0]._nVerts];
	_face[0]._vert[0]._vertIndex = 1;
	_face[0]._vert[1]._vertIndex = 2;
	_face[0]._vert[2]._vertIndex = 3;
	for(i = 0; i<_face[0]._nVerts ; i++)
		_face[0]._vert[i]._colorIndex = 0;
	

	_face[1]._nVerts = 3;
	_face[1]._vert = new VertexID[_face[1]._nVerts];
	_face[1]._vert[0]._vertIndex = 0;	
	_face[1]._vert[1]._vertIndex = 2;
	_face[1]._vert[2]._vertIndex = 1;
	for(i = 0; i<_face[1]._nVerts ; i++)
		_face[1]._vert[i]._colorIndex = 1;

	
	_face[2]._nVerts = 3;
	_face[2]._vert = new VertexID[_face[2]._nVerts];
	_face[2]._vert[0]._vertIndex = 0;
	_face[2]._vert[1]._vertIndex = 3;
	_face[2]._vert[2]._vertIndex = 2;
	for(i = 0; i<_face[2]._nVerts ; i++)
		_face[2]._vert[i]._colorIndex = 2;


	_face[3]._nVerts = 3;
	_face[3]._vert = new VertexID[_face[3]._nVerts];
	_face[3]._vert[0]._vertIndex = 1;
	_face[3]._vert[1]._vertIndex = 3;
	_face[3]._vert[2]._vertIndex = 0;
	for(i = 0; i<_face[3]._nVerts ; i++)
		_face[3]._vert[i]._colorIndex = 3;
}
void Mesh::DrawWireframe() {
	//glColor3f(0,0,0);
	glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	for (int f = 0; f < _numFaces; f++) {
		glBegin(GL_POLYGON);
		for (int v = 0; v < _face[f]._nVerts; v++) {
			int		iv = _face[f]._vert[v]._vertIndex;

			glVertex3f(_pt[iv].x, _pt[iv].y, _pt[iv].z);
		}
		glEnd();
	}
}
void Mesh::DrawColor() {
	glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	for (int f = 0; f < _numFaces; f++) {
		glBegin(GL_POLYGON);
		for (int v = 0; v < _face[f]._nVerts; v++) {
			int	iv = _face[f]._vert[v]._vertIndex;
			int	ic = _face[f]._vert[v]._colorIndex;
			glColor3f(ColorArr[ic][0], ColorArr[ic][1], ColorArr[ic][2]); 
			glVertex3f(_pt[iv].x, _pt[iv].y, _pt[iv].z);
		}
		glEnd();
	}
}
void Mesh::DrawShadowColor() {
	glColor3f(0, 0, 0); 
	glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	for (int f = 0; f < _numFaces; f++) {
		glBegin(GL_POLYGON);
			for (int v = 0; v < _face[f]._nVerts; v++) {
				int		iv = _face[f]._vert[v]._vertIndex;
				int		ic = _face[f]._vert[v]._colorIndex;
				
				glVertex3f(_pt[iv].x, _pt[iv].y, _pt[iv].z);
			}
		glEnd();
	}
}
void Mesh::DrawPentafaceVertex(int anIndexFace, int aVer1, int aVer2, int aVer3, int aVer4, int aVer5) {
	_face[anIndexFace]._nVerts = 5;
	_face[anIndexFace]._vert = new VertexID[_face[0]._nVerts];
	_face[anIndexFace]._vert[0]._vertIndex = aVer1;
	_face[anIndexFace]._vert[1]._vertIndex = aVer2;
	_face[anIndexFace]._vert[2]._vertIndex = aVer3;
	_face[anIndexFace]._vert[3]._vertIndex = aVer4;
	_face[anIndexFace]._vert[4]._vertIndex = aVer5;
	for(int i = 0; i < _face[anIndexFace]._nVerts ; i++) _face[anIndexFace]._vert[i]._colorIndex = anIndexFace;
}
void Mesh::CreateDodecahedron(float fRadius) {
	float m = 1 / sqrt(3.0) * fRadius;
	float r1 = 1 * m;
	float phi1 = goldenRatio * m;
	float iphi1 = (1.0 / goldenRatio) * m;

	_numVerts = 20;
	_pt = new Point3[_numVerts];
	_pt[0].set(-r1, -r1, -r1);
	_pt[1].set(r1, -r1, -r1);
	_pt[2].set(r1, r1, -r1);
	_pt[3].set(-r1, r1, -r1);

	_pt[4].set(-r1, -r1, r1);
	_pt[5].set(r1, -r1, r1);
	_pt[6].set(r1, r1, r1);
	_pt[7].set(-r1, r1, r1);

	_pt[8].set(0, -iphi1, -phi1);
	_pt[9].set(0, -iphi1, phi1);
	_pt[10].set(0, iphi1, phi1);
	_pt[11].set(0, iphi1, -phi1);

	_pt[12].set(-iphi1, -phi1, 0);
	_pt[13].set(iphi1, -phi1, 0);
	_pt[14].set(iphi1, phi1, 0);
	_pt[15].set(-iphi1, phi1, 0);

	_pt[16].set(-phi1, 0, -iphi1);
	_pt[17].set(phi1, 0, -iphi1);
	_pt[18].set(phi1, 0, iphi1);
	_pt[19].set(-phi1, 0, iphi1);

	_numFaces = 12;
	_face = new Face[_numFaces];
	
	int index = 0;
	DrawPentafaceVertex(index, 1, 8, 11, 2, 17); ++index;
	DrawPentafaceVertex(index, 2, 11, 3, 15, 14); ++index;
	
	DrawPentafaceVertex(index, 0, 16, 3, 11, 8); ++index;
	DrawPentafaceVertex(index, 8, 1, 13, 12, 0); ++index;

	DrawPentafaceVertex(index, 17, 18, 5, 13, 1); ++index;
	DrawPentafaceVertex(index, 6, 18, 17, 2, 14); ++index;

	DrawPentafaceVertex(index, 9, 10, 7, 19, 4); ++index;
	DrawPentafaceVertex(index, 19, 7, 15, 3, 16); ++index;

	DrawPentafaceVertex(index, 12, 4, 19, 16, 0); ++index;
	DrawPentafaceVertex(index, 13, 5, 9, 4, 12); ++index;

	DrawPentafaceVertex(index, 5, 18, 6, 10, 9); ++index;
	DrawPentafaceVertex(index, 6, 14, 15, 7, 10);
}
void Mesh::DrawTrifaceVertex(int anIndexFace, int aVer1, int aVer2, int aVer3) {
	_face[anIndexFace]._nVerts = 3;
	_face[anIndexFace]._vert = new VertexID[_face[0]._nVerts];
	_face[anIndexFace]._vert[0]._vertIndex = aVer1;
	_face[anIndexFace]._vert[1]._vertIndex = aVer2;
	_face[anIndexFace]._vert[2]._vertIndex = aVer3;
	for(int i = 0; i<_face[anIndexFace]._nVerts ; i++) _face[anIndexFace]._vert[i]._colorIndex = anIndexFace;
}
	// (0,±1,±ϕ)
	// (±1,±ϕ,0)
	// (±ϕ,0,±1) where ϕ is the golden ratio.

	// (±ϕ, ±1, 0), (0, ±ϕ, ±1), (±1, 0, ±ϕ).
void Mesh::CreateIcosahedron(float fRadius) {
	float m = 1 / sqrt(3.0) * fRadius;
	float r1 = 1 * m;
	float phi1 = goldenRatio * m;

	_numVerts = 12;
	_pt = new Point3[_numVerts];

	_pt[0].set(0, -phi1, r1);
	_pt[1].set(0, -phi1, -r1);
	_pt[2].set(0, phi1, r1);
	_pt[3].set(0, phi1, -r1);

	_pt[4].set(phi1, -r1, 0);
	_pt[5].set(phi1, r1, 0);
	_pt[6].set(-phi1, -r1, 0);
	_pt[7].set(-phi1, r1, 0);

	_pt[8].set(-r1, 0, phi1);
	_pt[9].set(r1, 0, phi1);
	_pt[10].set(r1, 0, -phi1);
	_pt[11].set(-r1, 0, -phi1);

	_numFaces = 20;
	_face = new Face[_numFaces];
	
	int index = 0;

	DrawTrifaceVertex(index, 0, 9, 8); ++index;
	DrawTrifaceVertex(index, 0, 4, 9); ++index;
	DrawTrifaceVertex(index, 0, 1, 4); ++index;
	DrawTrifaceVertex(index, 10, 4, 1); ++index;
	DrawTrifaceVertex(index, 9, 4, 5); ++index;
	DrawTrifaceVertex(index, 10, 5, 4); ++index;
	DrawTrifaceVertex(index, 10, 3, 5); ++index;
	DrawTrifaceVertex(index, 2, 9, 5); ++index;
	DrawTrifaceVertex(index, 2, 5, 3); ++index;
	DrawTrifaceVertex(index, 9, 2, 8); ++index; // danger

	DrawTrifaceVertex(index, 0, 8, 6); ++index;
	DrawTrifaceVertex(index, 0, 6, 1); ++index;
	DrawTrifaceVertex(index, 1, 6, 11); ++index;
	DrawTrifaceVertex(index, 1, 11, 10); ++index;
	DrawTrifaceVertex(index, 10, 11, 3); ++index;
	DrawTrifaceVertex(index, 3, 7, 2); ++index;
	DrawTrifaceVertex(index, 2, 7, 8); ++index;
	DrawTrifaceVertex(index, 11, 6, 7); ++index;
	DrawTrifaceVertex(index, 7, 6, 8); ++index;
	DrawTrifaceVertex(index, 3, 11, 7);
}
void Mesh::DrawOctafaceVertex(int anIndexFace, int aVer1, int aVer2, int aVer3, int aVer4, int aVer5, int aVer6, int aVer7, int aVer8) {
	_face[anIndexFace]._nVerts = 8;
	_face[anIndexFace]._vert = new VertexID[_face[0]._nVerts];
	_face[anIndexFace]._vert[0]._vertIndex = aVer1;
	_face[anIndexFace]._vert[1]._vertIndex = aVer2;
	_face[anIndexFace]._vert[2]._vertIndex = aVer3;
	_face[anIndexFace]._vert[3]._vertIndex = aVer4;
	_face[anIndexFace]._vert[4]._vertIndex = aVer5;
	_face[anIndexFace]._vert[5]._vertIndex = aVer6;
	_face[anIndexFace]._vert[6]._vertIndex = aVer7;
	_face[anIndexFace]._vert[7]._vertIndex = aVer8;
	for(int i = 0; i<_face[anIndexFace]._nVerts ; i++) _face[anIndexFace]._vert[i]._colorIndex = anIndexFace;
}
void Mesh::CreateTruncatedCube(float fSize, float fCut) {
	int i;
	_numVerts = 24;
	_pt = new Point3[_numVerts];
	_pt[0].set(fSize - fCut, fSize, fSize);
	_pt[1].set(fSize, fSize - fCut, fSize);
	_pt[2].set(fSize, fSize, fSize - fCut);

	_pt[3].set(fSize - fCut, -fSize, fSize);
	_pt[4].set( fSize, -fSize + fCut, fSize);
	_pt[5].set( fSize, -fSize, fSize - fCut);

	_pt[6].set(fSize - fCut, -fSize, -fSize);
	_pt[8].set(fSize, -fSize + fCut, -fSize);
	_pt[7].set(fSize, -fSize, -fSize + fCut);

	_pt[9].set( fSize - fCut, fSize, -fSize);
	_pt[11].set( fSize, fSize - fCut, -fSize);
	_pt[10].set( fSize, fSize, -fSize + fCut);

	_pt[12].set(-fSize + fCut, fSize, fSize);
	_pt[14].set(-fSize, fSize - fCut, fSize);
	_pt[13].set(-fSize, fSize, fSize - fCut);

	_pt[15].set( -fSize + fCut, fSize, -fSize);
	_pt[17].set( -fSize, fSize - fCut, -fSize);
	_pt[16].set( -fSize, fSize, -fSize + fCut);

	_pt[18].set(-fSize + fCut, -fSize, fSize);
	_pt[19].set(-fSize, -fSize + fCut, fSize);
	_pt[20].set(-fSize, -fSize, fSize - fCut);

	_pt[21].set(-fSize + fCut, -fSize, -fSize);
	_pt[22].set(-fSize, -fSize + fCut, -fSize);
	_pt[23].set(-fSize, -fSize, -fSize + fCut);

	_numFaces= 6 + 8;
	_face = new Face[_numFaces];

	int index = 0;
	DrawOctafaceVertex(index, 1, 0, 12, 14, 19, 18, 3, 4); ++index;
	DrawOctafaceVertex(index, 8, 11, 10, 2, 1, 4, 5, 7); ++index;
	DrawOctafaceVertex(index, 12, 0, 2, 10, 9, 15, 16, 13); ++index;
	DrawOctafaceVertex(index, 14, 13, 16, 17, 22, 23, 20, 19); ++index;
	DrawOctafaceVertex(index, 17, 15, 9, 11, 8, 6, 21, 22); ++index;
	DrawOctafaceVertex(index, 5, 3, 18, 20, 23, 21, 6, 7); ++index;

	DrawTrifaceVertex(index, 0, 1, 2); ++index;
	DrawTrifaceVertex(index, 3, 5, 4); ++index;
	DrawTrifaceVertex(index, 6, 8, 7); ++index;
	DrawTrifaceVertex(index, 11, 9, 10); ++index;
	DrawTrifaceVertex(index, 14, 12, 13); ++index;
	DrawTrifaceVertex(index, 16, 15, 17); ++index;
	DrawTrifaceVertex(index, 22, 21, 23); ++index;
	DrawTrifaceVertex(index, 18, 19, 20); 
}
Vector3 shadowPoint(float x, float y, float z) {
	Vector3 c(x, y, z);
	return Vshadow(_S0, _A, c, _n);
}
void Mesh::CreateShadowTruncatedCube(float fSize, float fCut) {
	int i;
	_numVerts = 24;
	_pt = new Point3[_numVerts];
	
	Vector3 pt(shadowPoint(fSize - fCut, fSize, fSize));
	_pt[0].set(pt.x, pt.y, pt.z);
	pt.set(shadowPoint(fSize, fSize - fCut, fSize));
	_pt[1].set(pt.x, pt.y, pt.z);
	pt.set(shadowPoint(fSize, fSize, fSize - fCut));
	_pt[2].set(pt.x, pt.y, pt.z);

	pt.set(shadowPoint(fSize - fCut, -fSize, fSize));
	_pt[3].set(pt.x, pt.y, pt.z);
	pt.set(shadowPoint(fSize, -fSize + fCut, fSize));
	_pt[4].set(pt.x, pt.y, pt.z);
	pt.set(shadowPoint(fSize, -fSize, fSize - fCut));
	_pt[5].set(pt.x, pt.y, pt.z);

	pt.set(shadowPoint(fSize - fCut, -fSize, -fSize));
	_pt[6].set(pt.x, pt.y, pt.z);
	pt.set(shadowPoint(fSize, -fSize + fCut, -fSize));
	_pt[8].set(pt.x, pt.y, pt.z);
	pt.set(shadowPoint(fSize, -fSize, -fSize + fCut));
	_pt[7].set(pt.x, pt.y, pt.z);

	pt.set(shadowPoint(fSize - fCut, fSize, -fSize));
	_pt[9].set(pt.x, pt.y, pt.z);
	pt.set(shadowPoint(fSize, fSize - fCut, -fSize));
	_pt[11].set(pt.x, pt.y, pt.z);
	pt.set(shadowPoint(fSize, fSize, -fSize + fCut));
	_pt[10].set(pt.x, pt.y, pt.z);

	pt.set(shadowPoint(-fSize + fCut, fSize, fSize));
	_pt[12].set(pt.x, pt.y, pt.z);
	pt.set(shadowPoint(-fSize, fSize - fCut, fSize));
	_pt[14].set(pt.x, pt.y, pt.z);
	pt.set(shadowPoint(-fSize, fSize, fSize - fCut));
	_pt[13].set(pt.x, pt.y, pt.z);

	pt.set(shadowPoint(-fSize + fCut, fSize, -fSize));
	_pt[15].set(pt.x, pt.y, pt.z);
	pt.set(shadowPoint(-fSize, fSize - fCut, -fSize));
	_pt[17].set(pt.x, pt.y, pt.z);
	pt.set(shadowPoint(-fSize, fSize, -fSize + fCut));
	_pt[16].set(pt.x, pt.y, pt.z);

	pt.set(shadowPoint(-fSize + fCut, -fSize, fSize));
	_pt[18].set(pt.x, pt.y, pt.z);
	pt.set(shadowPoint(-fSize, -fSize + fCut, fSize));
	_pt[19].set(pt.x, pt.y, pt.z);
	pt.set(shadowPoint(-fSize, -fSize, fSize - fCut));
	_pt[20].set(pt.x, pt.y, pt.z);

	pt.set(shadowPoint(-fSize + fCut, -fSize, -fSize));
	_pt[21].set(pt.x, pt.y, pt.z);
	pt.set(shadowPoint(-fSize, -fSize + fCut, -fSize));
	_pt[22].set(pt.x, pt.y, pt.z);
	pt.set(shadowPoint(-fSize, -fSize, -fSize + fCut));
	_pt[23].set(pt.x, pt.y, pt.z);

	_numFaces= 6 + 8;
	_face = new Face[_numFaces];

	int index = 0;
	//glColor3f(0, 0, 0);
	//glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

	DrawOctafaceVertex(index, 1, 0, 12, 14, 19, 18, 3, 4); ++index;
	DrawOctafaceVertex(index, 8, 11, 10, 2, 1, 4, 5, 7); ++index;
	DrawOctafaceVertex(index, 12, 0, 2, 10, 9, 15, 16, 13); ++index;
	DrawOctafaceVertex(index, 14, 13, 16, 17, 22, 23, 20, 19); ++index;
	DrawOctafaceVertex(index, 17, 15, 9, 11, 8, 6, 21, 22); ++index;
	DrawOctafaceVertex(index, 5, 3, 18, 20, 23, 21, 6, 7); ++index;

	DrawTrifaceVertex(index, 0, 1, 2); ++index;
	DrawTrifaceVertex(index, 3, 5, 4); ++index;
	DrawTrifaceVertex(index, 6, 8, 7); ++index;
	DrawTrifaceVertex(index, 11, 9, 10); ++index;
	DrawTrifaceVertex(index, 14, 12, 13); ++index;
	DrawTrifaceVertex(index, 16, 15, 17); ++index;
	DrawTrifaceVertex(index, 22, 21, 23); ++index;
	DrawTrifaceVertex(index, 18, 19, 20); 
}

///////////////////////////////////
Mesh	planeY;
Mesh	cylBase;
Mesh	cuboidBase;
Mesh    cylAxis;
Mesh	USupporter;
Mesh	cuboidAxis;
Mesh	cylDisk;
Mesh	sphere1InDisk;
Mesh	sphere2InDisk;
Mesh	sphere3InDisk;
Mesh	sphere;
Mesh	dodecahedron;
Mesh	icosahedron;
Mesh	truncatedCube;
Mesh	shadowTruncatedCube;

Mesh	cube;

void mySetupCameraVolume(int aNType) {
	if (aNType == 4) {
		glMatrixMode(GL_PROJECTION);			// set projection matrix current matrix
		glLoadIdentity();						// reset projection matrix

		// calculate aspect ratio of window
		gluPerspective(60.0f, (GLfloat)screenWidth / (GLfloat)screenHeight, 1.0f, 1000.0f);
	}
	else {
		glMatrixMode(GL_PROJECTION);			// set projection matrix current matrix
		glLoadIdentity();						// reset projection matrix
		glOrtho(-5, 5, -5, 5, -1000, 1000);
	}
	
}


static GLfloat nGround[4] = {0, 1, 0, 0};
/* Create a matrix that will project the desired shadow. */
void shadowTransform(float light[]) {
// This function updates the current matrix so that
// subsequently drawn objects are projected (using
// light[] as the center or direction of projection)
// onto the plane given by
//
// n[0]*x + n[1]*y + n[2]*z + n[3] = 0.
//
// The location or direction of the light is given by
// the light[] vector. If the 4th component (light[3])
// is 1 (or other non-zero value), then the light is
// located at a particular point, the center of projection
// for the shadow. On the other hand, if the 4th component
// is 0 then the light is assumed to be a directional
// source and the light vector specifies the direction of
// the light.
	float m[16];

	const float k = light[1];
	for (int i = 0; i < 16; i++) m[i] = 0;
	for (int i = 0; i < 4; i++) m[4 + i] = -light[i];
	for (int i = 0; i < 4; i++) m[5 * i] += k;
	
	glMultMatrixf(m);
}
void changeCameraPos() {
	camera_X = camera_dis * cos(DEG2RAD * camera_angle);
	camera_Y = camera_height;
	camera_Z = camera_dis * sin(DEG2RAD * camera_angle);
}
void mySpecialKeyboard(int aKey, int aMouseX, int aMouseY) {
	switch (aKey) {
	case GLUT_KEY_UP:
		camera_height += 0.1;
		changeCameraPos();
		break;
	case GLUT_KEY_DOWN:
		camera_height -= 0.1;
		changeCameraPos();
		break;
	case GLUT_KEY_RIGHT:
		camera_angle += 2;
		changeCameraPos();
		break;
	case GLUT_KEY_LEFT:
		camera_angle -= 2;
		changeCameraPos();
		break;
	}
	glutPostRedisplay();
}
static int _ichosen = -1;  

void changeColor(GLfloat anObject[4], float a, float b, float c, float d) {
	anObject[0] = a;
	anObject[1] = b;
	anObject[2] = c;
	anObject[3] = d;
}
void controlLight1(GLfloat aLight[4], float a, float b, float c, float d) {
	aLight[0] = a;
	aLight[1] = b;
	aLight[2] = c;
	aLight[3] = d;
}
void myKeyboard(unsigned char aKey, int aX, int aY) {
    switch(aKey) {
	case 'r':
	case 'R':
		if (_ichosen == 1) changeColor(sphereShapeColor, 1, 0, 0, 0.5);
		if (_ichosen == 2) changeColor(dodecahedronShapeColor, 1, 0, 0, 0.5);
		if (_ichosen == 3) changeColor(icosahedronShapeColor, 1, 0, 0, 0.5);
		if (_ichosen == 4) changeColor(truncatedCubeShapeColor, 1, 0, 0, 0.5);
		break;
	case 'g':
	case 'G':
		if (_ichosen == 1) changeColor(sphereShapeColor, 0, 1, 0, 0.5);
		if (_ichosen == 2) changeColor(dodecahedronShapeColor, 0, 1, 0, 0.5);
		if (_ichosen == 3) changeColor(icosahedronShapeColor, 0, 1, 0, 0.5);
		if (_ichosen == 4) changeColor(truncatedCubeShapeColor, 0, 1, 0, 0.5);
		break;
	case 'b':
	case 'B':
		if (_ichosen == 1) changeColor(sphereShapeColor, 0, 0, 1, 0.5);
		if (_ichosen == 2) changeColor(dodecahedronShapeColor, 0, 0, 1, 0.5);
		if (_ichosen == 3) changeColor(icosahedronShapeColor, 0, 0, 1, 0.5);
		if (_ichosen == 4) changeColor(truncatedCubeShapeColor, 0, 0, 1, 0.5);
		break;
	case 'p':
	case 'P':
		if (_ichosen == 1) changeColor(sphereShapeColor, 1, 0, 1, 0.5);
		if (_ichosen == 2) changeColor(dodecahedronShapeColor, 1, 0, 1, 0.5);
		if (_ichosen == 3) changeColor(icosahedronShapeColor, 1, 0, 1, 0.5);
		if (_ichosen == 4) changeColor(truncatedCubeShapeColor, 1, 0, 1, 0.5);
		break;
	case '+':
		camera_dis += 0.1;
		changeCameraPos();
		break;
	case '-':
		camera_dis -= 0.1;
		changeCameraPos();
		break;
	case 'l':
	case 'L':
		turnLight1 = !turnLight1;
		if (turnLight1) controlLight1(lightPosition2, -12, 32, -24, 1);
		else controlLight1(lightPosition2, 4, 32, 24, 1);
		break;
	case 'a':
	case 'A':
		isRunning = !isRunning;
		break;
	case '1':
		cylBaseRotateAngle += 2;
		if(cylBaseRotateAngle > 360)
			cylBaseRotateAngle -= 360;
		break;
	case '2':
		cylBaseRotateAngle -= 2;
		if(cylBaseRotateAngle < 0)
			cylBaseRotateAngle += 360;
		break;
	case '3':
		cylAxisRotateAngle += 2;
		if(cylAxisRotateAngle > 360)
			cylAxisRotateAngle -= 360;
		break;
	case '4':
		cylAxisRotateAngle -= 2;
		if(cylAxisRotateAngle < 0)
			cylAxisRotateAngle += 360;
		break;
	case '5':
		cylPlateRotateAngle -= 2;
		if(cylPlateRotateAngle < 0)
			cylPlateRotateAngle += 360;
		break;
	case '6':
		cylPlateRotateAngle += 2;
		if(cylPlateRotateAngle > 360)
			cylPlateRotateAngle -= 360;
		break;
	case 'w':
	case 'W':
		bWireFrame = !bWireFrame;
		break;
	}
    glutPostRedisplay();
}
void processHits(GLint hits, GLuint buffer[]) {
	unsigned int i, j;
	GLuint names, *ptr;
	//float min_z_min;
	ptr = (GLuint *)buffer;
	_ichosen = -1;
	for (i = 0; i < hits; i++) {
		names = *ptr;
		ptr += 3;
		for (j = 0; j < names; j++) {
			if(*ptr==1) _ichosen = 1;
			else if(*ptr==2) _ichosen = 2;
			else if(*ptr==3) _ichosen = 3;
			else if(*ptr==4) _ichosen = 4;
			/* go to next hit record */
			ptr++;
		}
	}
	//ptr = (GLuint*)buffer;
}
void mouse(int button, int state, int x, int y) {
	//mouseX = x;
	//mouseY = y;

//	GLuint nameBuffer[SIZE]; /* define SIZE elsewhere */
//	GLint hits;
//	GLint viewport[4];
//	if (button == GLUT_LEFT_BUTTON && state == GLUT_DOWN) {
//		/* initialize the name stack */
//		glInitNames( );
//		glPushName( 0 );
//		glSelectBuffer( SIZE, nameBuffer);
//		(void)glRenderMode(GL_SELECT);
		/* set up viewing for selection mode */
//		glGetIntegerv(GL_VIEWPORT, viewport);
//		glMatrixMode(GL_PROJECTION);
		/* save original viewing matrix */
//		glPushMatrix();
//			glLoadIdentity();
			/* N x N pick area around cursor */
			/* must invert mouse y to get in world coords */
//			gluPickMatrix((GLdouble)x, (GLdouble)(viewport[3] - y), N, N, viewport);
			/* same clipping window as in reshape callback */
			//gluOrtho2D (xmin, xmax,ymin, ymax) ;
//			gluPerspective(60.0, viewport[2] / viewport[3], 0.10, 2000.0);
//			draw(GL_SELECT);
//			glMatrixMode(GL_PROJECTION);
		/* restore viewing matrix */
//		glPopMatrix();
//		glFlush();
		/* return to normal render mode */
//		hits = glRenderMode(GL_RENDER);
		/* process hits from selection mode rendering */
//		processHits(hits, nameBuffer);
		/* normal render */
	//	glutPostRedisplay();


	GLuint selectBuf[BUFSIZE];
	GLint hits;
	GLint viewport[4];
	if (button != GLUT_LEFT_BUTTON || state != GLUT_DOWN)
		return;
	(void)glRenderMode(GL_SELECT);
	/* initialize the name stack */
	glInitNames();
	glPushName(0);
	glSelectBuffer(BUFSIZE, selectBuf);
	/* set up viewing for selection mode */
	glGetIntegerv(GL_VIEWPORT, viewport);
	glMatrixMode(GL_PROJECTION);
	/* save original viewing matrix */
	glPushMatrix();
		glLoadIdentity();
		/* N x N pick area around cursor */
		/* must invert mouse y to get in world coords */
		gluPickMatrix((GLdouble)x, (GLdouble)(viewport[3] - y), 0.001, 0.001, viewport);
		/* same clipping window as in reshape callback */
		gluPerspective(60.0, viewport[2] / viewport[3], 0.10, 2000.0);
		//glOrtho(-10, 10, -10, 10, -1000, 1000);
		draw(GL_SELECT);
		glMatrixMode(GL_PROJECTION);
	/* restore viewing matrix */
	glPopMatrix();
	glFlush();
	/* return to normal render mode */
	hits = glRenderMode(GL_RENDER);
	/* process hits from selection mode rendering */
	processHits(hits, selectBuf);
	/* normal render */
	glutPostRedisplay();

	
}
void timerAngle(float &aPrevAngle, float &aCurrentAngle, float anIncreaseAngle) {
	if (aCurrentAngle == 360) {
		aPrevAngle = 360;
		aCurrentAngle = 358;
	}
	else if (aCurrentAngle == 0) {
		aPrevAngle = -anIncreaseAngle;
		aCurrentAngle = 0;
	}
	if (aCurrentAngle > aPrevAngle) {
		aPrevAngle += anIncreaseAngle;
		aCurrentAngle += anIncreaseAngle;
	}
	else if (aCurrentAngle < aPrevAngle) {
		aPrevAngle -= anIncreaseAngle;
		aCurrentAngle -= anIncreaseAngle;
	}
}

float prevCylBaseRotateAngle = cylBaseRotateAngle - 2;
float prevCylAxisRotateAngle = cylAxisRotateAngle - 2 * 2;
float prevCylPlateRotateAngle = cylPlateRotateAngle - 2 * 3;

void onTimer(int aValue) {
	if (isRunning) {
		timerAngle(prevCylBaseRotateAngle, cylBaseRotateAngle, 2);
		timerAngle(prevCylAxisRotateAngle, cylAxisRotateAngle, 2 * 2);
		timerAngle(prevCylPlateRotateAngle, cylPlateRotateAngle, 2 * 3);

		glutPostRedisplay();
	}
	glutTimerFunc(100, onTimer, 0);
}
void setupMaterial(float ambient[], float diffuse[], float specular[], float shiness)
{
	glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, ambient);
	glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, diffuse);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, specular);
	glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, shiness);
}
void setLight() {
	GLfloat	lightDiffuse[]={1.0f, 1.0f, 1.0f, 1.0f};
	GLfloat	lightSpecular[]={1.0f, 1.0f, 1.0f, 1.0f};
	GLfloat	lightAmbient[]={0.4f, 0.4f, 0.4f, 1.0f};

	GLfloat light_position1[]={4.0f, 32.0f, 24.0f, 0.0f};
	GLfloat light_position2[] ={-12.0f, 32.0f, -24.0f, 0.0f};

	glLightfv(GL_LIGHT0, GL_POSITION, light_position1);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, lightDiffuse);
	glLightfv(GL_LIGHT0, GL_AMBIENT, lightAmbient);
	glLightfv(GL_LIGHT0, GL_SPECULAR, lightSpecular);

	glLightfv(GL_LIGHT1, GL_POSITION, light_position2);
	glLightfv(GL_LIGHT1, GL_DIFFUSE, lightDiffuse);
	glLightfv(GL_LIGHT1, GL_AMBIENT, lightAmbient);
	glLightfv(GL_LIGHT1, GL_SPECULAR, lightSpecular);

	glEnable(GL_LIGHTING);
}
void drawcube() {
	glPushMatrix();
		glDisable(GL_LIGHTING);
			glColor3f(0, 0, 1);
			glLineWidth(2.0);
			cube.DrawWireframe();
		glEnable(GL_LIGHTING);
	glPopMatrix();
	glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
}
void drawSquare() {
	glColor3f (0, 0, 0);
	glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	glBegin(GL_POLYGON);
		glVertex3f(-15, 0, -15);
		glVertex3f(-15, 0, 15);
		glVertex3f(15, 0, 15);
		glVertex3f(15, 0, -15);
	glEnd();
}
void drawFloor() {
	glPushMatrix();	
		//glTranslated(0, 0, 0);
		glDisable(GL_LIGHTING);
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D,_floorTex.texID);
		glColor4f(1, 1, 1, 1.0);
		loadTextures();

		if(bWireFrame) {
			drawSquare();
		} else {
			
			glEnable(GL_BLEND);
				glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
				glColor4f(1.0, 1.0, 1.0, 0.5);
				glBegin(GL_QUADS);
					//glTexCoord2f(0, _floorTex.height);
					glTexCoord2f(0, 1);
					glVertex3f(-15, 0, -15);
					//glTexCoord2f(_floorTex.width, _floorTex.height);
					glTexCoord2f(1, 1);
					glVertex3f(-15, 0, 15);
					//glTexCoord2f(_floorTex.width, 0);
					glTexCoord2f(0, 1);
					glVertex3f(15, 0, 15);
					glTexCoord2f(0, 0);
					glVertex3f(15, 0, -15);
				glEnd();
			glDisable(GL_BLEND);
		}
		/*glRotated(-90, 1, 0, 0);
		/*glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		for (int i = 0; i < 10; i += 2) 
			for (int j = 0; j < 10; j += 2) {
					drawSquare(i,j);
					drawSquare(i-1,j+1);
			}
			*/

		

		glDisable(GL_TEXTURE_2D);
		glEnable(GL_LIGHTING);

	glPopMatrix();
}
void drawSphere(GLint mode) {
	glPushMatrix();
		//////////////////////////////////
		glTranslated(sphere._slideX, sphere._slideY, sphere._slideZ);
		if (mode == GL_SELECT){
			glLoadName(1);
		}
		if (_ichosen == 1){
			drawcube();
		}
		if (bWireFrame) sphere.DrawWireframe();
		else {
			setupMaterial(mat_ambient, sphereShapeColor, mat_specular, mat_shininess);
			sphere.Draw();
		}
	glPopMatrix();
}
void drawSphere() {
	glPushMatrix();
		//////////////////////////////////
		glTranslated(sphere._slideX, sphere._slideY, sphere._slideZ);
		if (bWireFrame) sphere.DrawWireframe();
		else {
			setupMaterial(mat_ambient, sphereShapeColor, mat_specular, mat_shininess);
			sphere.Draw();
		}
	glPopMatrix();
}
void drawDodecahedron(GLint mode) {
	glPushMatrix();
		//////////////////////////////////
		glTranslated(dodecahedron._slideX, dodecahedron._slideY, dodecahedron._slideZ);
		if (mode == GL_SELECT){
			glLoadName(2);
		}
		if (_ichosen == 2){
			drawcube();
		}
		if (bWireFrame) dodecahedron.DrawWireframe();
		else {
			setupMaterial(mat_ambient, dodecahedronShapeColor, mat_specular, mat_shininess);
			dodecahedron.Draw();
		}
	glPopMatrix();
}
void drawDodecahedron() {
	glPushMatrix();
		//////////////////////////////////
		glTranslated(dodecahedron._slideX, dodecahedron._slideY, dodecahedron._slideZ);
		if (bWireFrame) dodecahedron.DrawWireframe();
		else {
			setupMaterial(mat_ambient, dodecahedronShapeColor, mat_specular, mat_shininess);
			dodecahedron.Draw();
		}
	glPopMatrix();
}
void drawIcosahedron(GLint mode) {
	glPushMatrix();
		//////////////////////////////////
		glTranslated(icosahedron._slideX, icosahedron._slideY, icosahedron._slideZ);
		if (mode == GL_SELECT){
			glLoadName(3);
		}
		if (_ichosen == 3){
			drawcube();
		}
		if (bWireFrame) icosahedron.DrawWireframe();
		else {
			setupMaterial(mat_ambient, icosahedronShapeColor, mat_specular, mat_shininess);
			icosahedron.Draw();
		}
	glPopMatrix();
}
void drawIcosahedron() {
	glPushMatrix();
		//////////////////////////////////
		glTranslated(icosahedron._slideX, icosahedron._slideY, icosahedron._slideZ);
		if (bWireFrame) icosahedron.DrawWireframe();
		else {
			setupMaterial(mat_ambient, icosahedronShapeColor, mat_specular, mat_shininess);
			icosahedron.Draw();
		}
	glPopMatrix();
}
void drawShadowTruncatedCube() {
	glPushMatrix();
		glTranslated(shadowTruncatedCube._slideX, shadowTruncatedCube._slideY, shadowTruncatedCube._slideZ);
		if (bWireFrame) shadowTruncatedCube.DrawWireframe();
		else {
			
			shadowTruncatedCube.Draw();
		}
		
	glPopMatrix();

}
void drawTruncatedCube(GLint mode) {
	glPushMatrix();
		
		//////////////////////////////////
		glTranslated(truncatedCube._slideX, truncatedCube._slideY, truncatedCube._slideZ);
		if (mode == GL_SELECT){
			glLoadName(4);
		}
		if (_ichosen == 4){
			drawcube();
		}
		//shadowTruncatedCube.DrawColor();
		if (bWireFrame) truncatedCube.DrawWireframe();
		else {
			setupMaterial(mat_ambient, truncatedCubeShapeColor, mat_specular, mat_shininess);
			truncatedCube.Draw();
		}
	glPopMatrix();
}
void drawTruncatedCube() {
	glPushMatrix();
		
		//////////////////////////////////
		glTranslated(truncatedCube._slideX, truncatedCube._slideY, truncatedCube._slideZ);
		//shadowTruncatedCube.DrawColor();
		if (bWireFrame) truncatedCube.DrawWireframe();
		else {
			setupMaterial(mat_ambient, truncatedCubeShapeColor, mat_specular, mat_shininess);
			truncatedCube.Draw();
		}
	glPopMatrix();
}
void drawCylinderBase() {
	glPushMatrix();
		setupMaterial(mat_ambient, red_diffuse, mat_specular, mat_shininess);
		glRotated(cylBaseRotateAngle, 0, 1, 0);
		//////////////////////////////////
		glTranslated(0, cylBase._slideY, 0);
		if (bWireFrame) cylBase.DrawWireframe();
		else cylBase.Draw();
	glPopMatrix();
}
void drawCuboidBase() {
	glPushMatrix();		
		setupMaterial(mat_ambient, blue_diffuse, mat_specular, mat_shininess);
		glRotated(cylBaseRotateAngle, 0, 1, 0);
		//////////////////////////////////
		glTranslated(0, cuboidBase._slideY, 0);
		if (bWireFrame) cuboidBase.DrawWireframe();
		else cuboidBase.Draw();
	glPopMatrix();
}
void drawCylAxis() {
	glPushMatrix();
		setupMaterial(mat_ambient, green_diffuse, mat_specular, mat_shininess);
		glRotated(cylBaseRotateAngle, 0, 1, 0);
		//////////////////////////////////
		glTranslated(0, cylAxis._slideY, cylAxis._slideZ);
		glRotatef(90, 1, 0, 0);
		glRotated(cylAxisRotateAngle, 0, 1, 0);
		if (bWireFrame) cylAxis.DrawWireframe();
		else cylAxis.Draw();
	glPopMatrix();
}
void drawUAxis() {
	glPushMatrix();
		setupMaterial(mat_ambient, peru_diffuse, mat_specular, mat_shininess);
		glRotated(cylBaseRotateAngle, 0, 1, 0);
		//////////////////////////////////
		glTranslated(0, USupporter._slideY, USupporter._slideZ);
		glRotated(cylAxisRotateAngle, 0, 0, 1);
		if (bWireFrame) USupporter.DrawWireframe();
		else USupporter.Draw();
	glPopMatrix();
}
void drawCuboidAxis() {
	glPushMatrix();
		setupMaterial(mat_ambient, orangered_diffuse, mat_specular, mat_shininess);
		glRotated(cylBaseRotateAngle, 0, 1, 0);
		//////////////////////////////////
		glTranslated(0, cuboidAxis._slideY, cuboidAxis._slideZ);
		glRotatef(90, 0, 0, 1);
		glRotated(cylAxisRotateAngle, 0, 0, 1);
		glRotated(cylPlateRotateAngle, 0, 1, 0);
		if (bWireFrame) cuboidAxis.DrawWireframe();
		else cuboidAxis.Draw();
	glPopMatrix();
}
void drawCylPlate() {
	glPushMatrix();
		setupMaterial(mat_ambient, gray_diffuse, mat_specular, mat_shininess);
		glRotated(cylBaseRotateAngle, 0, 1, 0);
		/////////////////////////////////
		glTranslated(0, cylDisk._slideY, cylDisk._slideZ);
		glRotatef(90, 0, 0, 1);
		glRotated(cylAxisRotateAngle, 0, 0, 1);
		glRotated(cylPlateRotateAngle, 0, 1, 0);
		if (bWireFrame) cylDisk.DrawWireframe();
		else cylDisk.Draw();
	glPopMatrix();
}
void drawSphere1InDisk() {
	glPushMatrix();
		setupMaterial(mat_ambient, red_diffuse, mat_specular, mat_shininess);
		glRotated(cylBaseRotateAngle, 0, 1, 0);
		/////////////////////////////////
		glTranslated(0, sphere1InDisk._slideY, sphere1InDisk._slideZ);

		float r = 0.4;
		float y = r * cos(DEG2RAD * -cylAxisRotateAngle) * sin(DEG2RAD * cylPlateRotateAngle);
		float x = r * sin(DEG2RAD * -cylAxisRotateAngle) * sin(DEG2RAD * cylPlateRotateAngle);
		float z = r * cos(DEG2RAD * -cylPlateRotateAngle);
		glTranslated(x, y, z);
		glRotated(cylAxisRotateAngle, 0, 0, 1);

		if (bWireFrame) sphere1InDisk.DrawWireframe();
		else sphere1InDisk.Draw();
	glPopMatrix();
}
void drawSphere2InDisk() {
	glPushMatrix();
		setupMaterial(mat_ambient, green_diffuse, mat_specular, mat_shininess);
		glRotated(cylBaseRotateAngle, 0, 1, 0);
		/////////////////////////////////
		glTranslated(0, sphere2InDisk._slideY, sphere2InDisk._slideZ);

		float r = 0.4;
		float y = r * cos(DEG2RAD * -cylAxisRotateAngle) * sin(DEG2RAD * (240 + cylPlateRotateAngle));
		float x = r * sin(DEG2RAD * -cylAxisRotateAngle) * sin(DEG2RAD * (240 + cylPlateRotateAngle));
		float z = r * cos(DEG2RAD * -(240 + cylPlateRotateAngle));

		glTranslated(x, y, z);
		
		if (bWireFrame) sphere2InDisk.DrawWireframe();
		else sphere2InDisk.Draw();
	glPopMatrix();
}
void drawSphere3InDisk() {
	glPushMatrix();
		setupMaterial(mat_ambient, blue_diffuse, mat_specular, mat_shininess);
		glRotated(cylBaseRotateAngle, 0, 1, 0);
		/////////////////////////////////
		glTranslated(0, sphere3InDisk._slideY, sphere3InDisk._slideZ);

		float r = 0.4;
		float y = r * cos(DEG2RAD * -cylAxisRotateAngle) * sin(DEG2RAD * (120 + cylPlateRotateAngle));
		float x = r * sin(DEG2RAD * -cylAxisRotateAngle) * sin(DEG2RAD * (120 + cylPlateRotateAngle));
		float z = r * cos(DEG2RAD * -(120 + cylPlateRotateAngle));

		glTranslated(x, y, z);

		if (bWireFrame) sphere3InDisk.DrawWireframe();
		else sphere3InDisk.Draw();

	glPopMatrix();
}
void drawAllShape(GLint mode) {
	glPushMatrix();
	//drawFloor();

	drawCylinderBase();
	drawCuboidBase();
	drawCylAxis();
	drawUAxis();
	drawCuboidAxis();
	drawCylPlate();
	drawSphere1InDisk();
	drawSphere2InDisk();
	drawSphere3InDisk();

	drawSphere(mode);
	drawDodecahedron(mode);
	//drawShadowTruncatedCube();
	drawIcosahedron(mode);
	drawTruncatedCube(mode);
	glPopMatrix();
	
}
void drawAllShadowShape() {
	glPushMatrix();
	//drawFloor();

	//drawCylinderBase();
	//drawCuboidBase();
	//drawCylAxis();
	//drawUAxis();
	//drawCuboidAxis();
	//drawCylPlate();
	//drawSphere1InDisk();
	//drawSphere2InDisk();
	//drawSphere3InDisk();

	drawSphere();
	drawDodecahedron();
	//drawShadowTruncatedCube();
	drawIcosahedron();
	drawTruncatedCube();
	glPopMatrix();
	
}
void drawReflectedImage(GLint mode) {
	glPushMatrix();
		setLight();
		glEnable(GL_NORMALIZE);
		glCullFace(GL_FRONT);
		glScalef(1.0, -1.0, 1.0);
		drawFloor(); 
		drawAllShape(mode);
		glDisable(GL_NORMALIZE);
		glCullFace(GL_BACK);
	glPopMatrix();
}
void drawRealImage(GLint mode) {
	glPushMatrix();
		setLight();
		drawFloor(); 
		drawAllShape(mode);
	glPopMatrix();
}
void DisplayOneView(int aNType, int aLeft, int aRight, int aTop, int aBottom) {
	//mySetupCameraVolume(aNType);
	//glViewport(aLeft, aTop, aRight - aLeft, aBottom - aTop);

	changeCameraPos(); 

	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();

	//code here
	switch (aNType) {
	case 1:
		gluLookAt(0, camera_dis, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0);
		break;
	case 2:
		gluLookAt(0, 0.0, camera_dis, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
		break;
	case 3:
		gluLookAt(camera_dis, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
		break;
	case 4:
		gluLookAt(camera_X, camera_Y, camera_Z, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
		break;
	}


	////////////////////////////////////////////////
	//drawReflectedImage(mode);
	//drawRealImage(mode);
}
void drawShadow(float light[4]) {
	glDisable(GL_DEPTH_TEST);
	glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glDisable(GL_LIGHTING);  /* Force the 50% black. */
    glColor4f(0.0, 0.0, 0.0, 1);
	glPushMatrix();
		glMatrixMode(GL_MODELVIEW);
		shadowTransform(light);
		//drawAllShape(GL_RENDER);
		drawAllShadowShape();
	glPopMatrix();
	glEnable(GL_DEPTH_TEST);
	glDisable(GL_STENCIL_TEST);
	//glEnable(GL_LIGHTING);
	glDisable(GL_BLEND);
	glEnable(GL_LIGHTING);
}
void draw(GLint mode) {
	if (b4View == false) DisplayOneView(4, 0, screenWidth, 0, screenHeight);
	else {
		DisplayOneView(1, 0, screenWidth / 2, 0, screenHeight / 2);
		DisplayOneView(2, 0, screenWidth / 2, screenHeight / 4, 3 * screenHeight / 4);
		DisplayOneView(3, screenWidth / 2, screenWidth, screenHeight / 4, 3 * screenHeight / 4);
		DisplayOneView(4, screenWidth / 2, screenWidth, 0, screenHeight / 2);
	}

	glEnable(GL_STENCIL_TEST);
	glStencilFunc(GL_ALWAYS, 1, 0xFF);
	glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);
	glClear(GL_STENCIL_BUFFER_BIT);
	/////////////////////////////////////////////////////
	// Draw shadow
	//drawShadow(lightPosition1);
	//drawShadow(lightPosition2);
	///////////////////////////////////////////////////////
	/*glPushMatrix();
	glDisable(GL_DEPTH_TEST);
	drawFloor();    // draw floor to the stencil buffer
	glEnable(GL_DEPTH_TEST);
	glPopMatrix();
	glColorMask(1, 1, 1, 1);
	glStencilFunc(GL_EQUAL, 1, 1);
	glPushMatrix();
		glScalef(1.0f, -1.0f, 1.0f);
		//drawFloor();
		drawAllShape(GL_RENDER);
	glPopMatrix();
	glDisable(GL_STENCIL_TEST);

	glPushMatrix();
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		drawFloor();    // draw floor second time
		glDisable(GL_BLEND);
	
		drawAllShape(mode);
	glPopMatrix();*/


	drawReflectedImage(mode);
	/////////////////////////////////////////////////////
	// Draw shadow
	drawShadow(lightPosition1);
	drawShadow(lightPosition2);
	///////////////////////////////////////////////////////
	drawRealImage(mode);

}
void myDisplay() {
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
	
	if (turnLight1 == false) glDisable(GL_LIGHT1);
	else glEnable(GL_LIGHT1);

	mySetupCameraVolume(4);
	//drawObjects(GL_RENDER);
//	shadowMatrix(floorShadow, floorPlane, lightPosition1);
	/* Render 50% black shadow color on top of whatever the
         floor appareance is. */
 /*   glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glDisable(GL_LIGHTING);  /* Force the 50% black. */
 /*   glColor4f(0.0, 0.0, 0.0, 0.5);

	glPushMatrix();
       /* Project the shadow. */
 /*       glMultMatrixf((GLfloat *) floorShadow);
        draw(GL_RENDER);
    glPopMatrix();

	glDisable(GL_BLEND);
	glEnable(GL_LIGHTING);
	*/
	draw(GL_RENDER);
	
	
	glFlush();
    glutSwapBuffers();
}
void setCamera() {
	float fHalfSize = 10;
	camera_angle = 90.0;
	camera_height = 2.5;
	camera_dis = 13;

	glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    //glColor3f(0.0f, 0.0f, 0.0f);

	glFrontFace(GL_CCW);
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(-fHalfSize, fHalfSize, -fHalfSize, fHalfSize, -1000, 1000);

	//glMatrixMode(GL_MODELVIEW);
}

bool zoom = false;
void reshape(int w, int h){
	//setup viewport
	//int size = min(width, height);

	
	if (w > h) {
		camera_dis += 5;
		zoom = true;
	}
	else {
		if (zoom) {
			zoom = false;
			camera_dis -= 5;
		}
	}

	glViewport(0, 0, w, h);
	/*glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	if (w <= h)
		gluOrtho2D(-2.0, 2.0, -2.0 * (GLfloat) h / (GLfloat) w, 2.0 * 		(GLfloat) h / (GLfloat) w);
	else 
		gluOrtho2D(-2.0 * (GLfloat) w / (GLfloat) h, 2.0 * 				(GLfloat) w / (GLfloat) h, -2.0, 2.0);
	glMatrixMode(GL_MODELVIEW); 

	*/
}
void myInit() {
	setLight();
	glEnable(GL_LIGHT0);
	glEnable(GL_LIGHT1);
	glMatrixMode(GL_PROJECTION);
	setCamera();
	glEnable(GL_NORMALIZE);
	//glShadeModel(GL_SMOOTH);
	glFrontFace(GL_CCW);
	glEnable(GL_DEPTH_TEST);
}
int _tmain(int argc, _TCHAR* argv[]) {
	cout << "1, 2: Rotate the base" << endl;
	cout << "3, 4: Rotate axes 1 (cylinder axes)" << endl;
	cout << "5, 6: Rotate axes 2 (cuboid axes)" << endl;
	cout << "W, w: Switch between wireframe and solid mode" << endl;
	cout << "A, a: Turn on/off animation mode" << endl;
	cout << "+   : to increase camera distance." << endl;
	cout << "-   : to decrease camera distance." << endl;
	cout << "up arrow  : to increase camera height." << endl;
	cout << "down arrow: to decrease camera height." << endl;
	cout << "<-        : to rotate camera clockwise." << endl;
	cout << "->        : to rotate camera counterclockwise." << endl;
	cout << "L, l: to turn on/turn off the second light source" << endl;

	glutInit(&argc, (char**)argv); //initialize the tool kit
	glutInitDisplayMode(GLUT_DOUBLE |GLUT_RGB | GLUT_DEPTH);//set the display mode
	glutInitWindowSize(screenWidth, screenHeight); //set window size
	glutInitWindowPosition(100, 100); // set window position on screen
	glutCreateWindow("Assignment2 - Huynh Tan Phuc (51202787)"); // open the screen window

	cylBase.CreateCylinder(40, cylBaseHeight, cylBaseRadius);
	cylBase._slideY = YPlanePos + cylBaseHeight / 2.0;
	cylBase.CalculateFacesNorm();
	cylBase.SetColor(1);

	cuboidBase.CreateCuboid(cuboidBaseSizeXZ / 2.0, cuboidBaseSizeY / 2.0, cuboidBaseSizeXZ / 2.0);
	cuboidBase._slideY = YPlanePos + cylBaseHeight + cuboidBaseSizeY / 2.0;
	cuboidBase.CalculateFacesNorm();
	cuboidBase.SetColor(0);

	cylAxis.CreateCylinder(40, cylAxisHeight, cylAxisRadius);
	cylAxis._slideY = cylBaseHeight + cuboidBaseSizeY - cylAxisOffset;
	cylAxis._slideZ = cylAxisHeight / 2 + cuboidBaseSizeXZ / 2.0;
	cylAxis.CalculateFacesNorm();
	cylAxis.SetColor(2);

	USupporter.CreateUShape(UShapeX / 2.0, UShapeY / 2.0, UShapeZ / 2.0, UShapeThick);
	USupporter._slideY = cylBaseHeight + cuboidBaseSizeY - cylAxisOffset;
	USupporter._slideZ = UShapeZ/2 + UShapeThick + cylAxisHeight + cuboidBaseSizeXZ / 2.0;
	USupporter.CalculateFacesNorm();
	USupporter.SetColor(3);

	cuboidAxis.CreateCuboid(cuboidAxisXZ / 2.0, cuboidAxisY / 2.0, cuboidAxisXZ / 2.0);
	cuboidAxis._slideY = cylBaseHeight + cuboidBaseSizeY - cylAxisOffset;
	cuboidAxis._slideZ = UShapeZ / 2 + 0.4 + UShapeThick + cylAxisHeight + cuboidBaseSizeXZ / 2.0;
	cuboidAxis.CalculateFacesNorm();
	cuboidAxis.SetColor(4);

	cylDisk.CreateCylinder(40, cylPlateHeight, cylPlateRadius);
	cylDisk._slideY = cylBaseHeight + cuboidBaseSizeY - cylAxisOffset;
	cylDisk._slideZ = UShapeZ / 2 + 0.4 + UShapeThick + cylAxisHeight + cuboidBaseSizeXZ / 2.0;
	cylDisk.CalculateFacesNorm();
	cylDisk.SetColor(5);

	sphere1InDisk.CreateSphere(40, 40, sphereRadius);
	sphere1InDisk._slideY = cylBaseHeight+cuboidBaseSizeY - cylAxisOffset;
	sphere1InDisk._slideZ = UShapeZ / 2 + 0.4 + UShapeThick + cylAxisHeight + cuboidBaseSizeXZ / 2.0;
	sphere1InDisk.CalculateFacesNorm();
	sphere1InDisk.SetColor(6);

	sphere2InDisk.CreateSphere(40, 40, sphereRadius);
	sphere2InDisk._rotateX = 0.4 * cos(PI / 180 * 30.0 + cylPlateRotateAngle / 180.0 * PI);
	sphere2InDisk._slideX = 0.4 * cos(PI / 180 * 30.0 + cylPlateRotateAngle / 180.0 * PI);
	sphere2InDisk._slideY = cylBaseHeight + cuboidBaseSizeY - cylAxisOffset;
	sphere2InDisk._slideZ = UShapeZ / 2 + 0.4 + UShapeThick + cylAxisHeight + cuboidBaseSizeXZ / 2.0;
	sphere2InDisk.CalculateFacesNorm();
	sphere2InDisk.SetColor(2);

	sphere3InDisk.CreateSphere(40, 40, sphereRadius);
	sphere3InDisk._slideY = cylBaseHeight + cuboidBaseSizeY - cylAxisOffset;
	sphere3InDisk._slideZ = UShapeZ / 2 + 0.4 + UShapeThick + cylAxisHeight + cuboidBaseSizeXZ / 2.0;
	sphere3InDisk.CalculateFacesNorm();
	sphere3InDisk.SetColor(0);

	sphere.CreateSphere(40, 40, sphereShapeRadius);
	sphere._slideX = cylBaseHeight + 3.0;
	sphere._slideY = sphereShapeRadius;
	sphere._slideZ = -3;
	sphere.CalculateFacesNorm();
	sphere.SetColor(0);

	dodecahedron.CreateDodecahedron(dodecahedronRadius);
	dodecahedron._slideX = -(cylBaseHeight + 3.0);
	dodecahedron._slideY = dodecahedronRadius;
	dodecahedron._slideZ = 3;
	dodecahedron.CalculateFacesNorm();
	dodecahedron.SetColor(0);

	icosahedron.CreateIcosahedron(icosahedronRadius);
	icosahedron._slideX = cylBaseHeight + 3.0;
	icosahedron._slideY = icosahedronRadius;
	icosahedron._slideZ = 3;
	icosahedron.CalculateFacesNorm();
	icosahedron.SetColor(0);

	truncatedCube.CreateTruncatedCube(truncatedCubeSize, truncatedCubeCut);
	truncatedCube._slideX = -(cylBaseHeight + 3.0);
	truncatedCube._slideY = truncatedCubeSize;
	truncatedCube._slideZ = -3;
	truncatedCube.CalculateFacesNorm();
	truncatedCube.SetColor(0);



	cube.CreateCuboid(0.5, 0.5, 0.5);

	myInit();

	glutReshapeFunc(reshape);
	glutDisplayFunc(myDisplay);
	glutTimerFunc(100, onTimer, 0);
	glutKeyboardFunc(myKeyboard);
    glutSpecialFunc(mySpecialKeyboard);
	glutMouseFunc(mouse);
	

	glutMainLoop();

	// Destruction
	planeY.~Mesh();
	cylBase.~Mesh();
	cuboidBase.~Mesh();
	cylAxis.~Mesh();
	USupporter.~Mesh();
	cuboidAxis.~Mesh();
	cylDisk.~Mesh();
	sphere1InDisk.~Mesh();
	sphere2InDisk.~Mesh();
	sphere3InDisk.~Mesh();
	sphere.~Mesh();
	dodecahedron.~Mesh();
	icosahedron.~Mesh();
	truncatedCube.~Mesh();

	return 0;

}

