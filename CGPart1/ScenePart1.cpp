// assignment1.cpp : Defines the entry point for the console application.
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

float ColorArr[COLORNUM][3] = {{1.0, 0.0, 0.0}, {0.0, 1.0, 0.0}, { 0.0,  0.0, 1.0}, 
							   {1.0, 1.0,  0.0}, { 1.0, 0.0, 1.0},{ 0.0, 1.0, 1.0}, 
							   {0.3, 0.3, 0.3}, {0.5, 0.5, 0.5}, { 0.9,  0.9, 0.9},
							   {1.0, 0.5,  0.5}, { 0.5, 1.0, 0.5},{ 0.5, 0.5, 1.0},
							   {0.0, 0.0, 0.0}, {1.0, 1.0, 1.0}};

bool isRunning = true;
int boundary = 0;

const int screenWidth = 600;
const int screenHeight= 600;
const float goldenRatio = (1 + sqrt(5.0)) / 2; 

bool bWireFrame = false;
bool b4View = false;

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
	void DrawVarityColor();
	void SetColor(int colorIdx);
	void CreateTetrahedron();
	void CreateCube(float fSize);
	void CreateCuboid(float fLength, float fWidth, float fHeight);
	void CreateCylinder(int nSegment, float fHeight, float fRadius);
	void CreateSphere(int nStacks, int nSlices, float fRadius);
	void CreateUShape(float	fSizeX, float fSizeY, float	fSizeZ, float fThick);
	void drawPentafaceVertex(int aNumOfFace, int aVer1, int aVer2, int aVer3, int aVer4, int aVer5);
	void CreateDodecahedron(float fRadius);
	void drawTrifaceVertex(int anIndexFace, int aVer1, int aVer2, int aVer3);
	void CreateIcosahedron(float fRadius);
	void drawOctafaceVertex(int aNumOfFace, int aVer1, int aVer2, int aVer3, int aVer4, int aVer5, int aVer6, int aVer7, int aVer8);
	void CreateTruncatedCube(float fSize, float fCut);
};
///////////////////////////////////
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
	_pt[9].set(-fSizeX+fThick, -fSizeY, -fSizeZ);
	_pt[10].set(-fSizeX+fThick, fSizeY, -fSizeZ);
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
void Mesh::CreateCuboid(float fLength, float fWidth, float fHeight)
{
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
void Mesh::CreateTetrahedron()
{
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
	glColor3f(0,0,0);
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
void Mesh::DrawVarityColor() {
	glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	for (int f = 0; f < _numFaces; f++) {
		glBegin(GL_POLYGON);
		for (int v = 0; v < _face[f]._nVerts; v++) {
			int		iv = _face[f]._vert[v]._vertIndex;
			int		ic = _face[f]._vert[v]._colorIndex;
			
			ic = f % COLORNUM;

			glColor3f(ColorArr[ic][0], ColorArr[ic][1], ColorArr[ic][2]); 
			glVertex3f(_pt[iv].x, _pt[iv].y, _pt[iv].z);
		}
		glEnd();
	}
}
void Mesh::drawPentafaceVertex(int anIndexFace, int aVer1, int aVer2, int aVer3, int aVer4, int aVer5) {
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
	drawPentafaceVertex(index, 1, 8, 11, 2, 17); ++index;
	drawPentafaceVertex(index, 2, 11, 3, 15, 14); ++index;
	
	drawPentafaceVertex(index, 0, 16, 3, 11, 8); ++index;
	drawPentafaceVertex(index, 8, 1, 13, 12, 0); ++index;

	drawPentafaceVertex(index, 17, 18, 5, 13, 1); ++index;
	drawPentafaceVertex(index, 6, 18, 17, 2, 14); ++index;

	drawPentafaceVertex(index, 9, 10, 7, 19, 4); ++index;
	drawPentafaceVertex(index, 19, 7, 15, 3, 16); ++index;

	drawPentafaceVertex(index, 12, 4, 19, 16, 0); ++index;
	drawPentafaceVertex(index, 13, 5, 9, 4, 12); ++index;

	drawPentafaceVertex(index, 5, 18, 6, 10, 9); ++index;
	drawPentafaceVertex(index, 6, 14, 15, 7, 10);
}
void Mesh::drawTrifaceVertex(int anIndexFace, int aVer1, int aVer2, int aVer3) {
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

	drawTrifaceVertex(index, 0, 9, 8); ++index;
	drawTrifaceVertex(index, 0, 4, 9); ++index;
	drawTrifaceVertex(index, 0, 1, 4); ++index;
	drawTrifaceVertex(index, 10, 4, 1); ++index;
	drawTrifaceVertex(index, 9, 4, 5); ++index;
	drawTrifaceVertex(index, 10, 5, 4); ++index;
	drawTrifaceVertex(index, 10, 3, 5); ++index;
	drawTrifaceVertex(index, 2, 9, 5); ++index;
	drawTrifaceVertex(index, 2, 5, 3); ++index;
	drawTrifaceVertex(index, 9, 2, 8); ++index; // danger

	drawTrifaceVertex(index, 0, 8, 6); ++index;
	drawTrifaceVertex(index, 0, 6, 1); ++index;
	drawTrifaceVertex(index, 1, 6, 11); ++index;
	drawTrifaceVertex(index, 1, 11, 10); ++index;
	drawTrifaceVertex(index, 10, 11, 3); ++index;
	drawTrifaceVertex(index, 3, 7, 2); ++index;
	drawTrifaceVertex(index, 2, 7, 8); ++index;
	drawTrifaceVertex(index, 11, 6, 7); ++index;
	drawTrifaceVertex(index, 7, 6, 8); ++index;
	drawTrifaceVertex(index, 3, 11, 7);
}
void Mesh::drawOctafaceVertex(int anIndexFace, int aVer1, int aVer2, int aVer3, int aVer4, int aVer5, int aVer6, int aVer7, int aVer8) {
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
	drawOctafaceVertex(index, 1, 0, 12, 14, 19, 18, 3, 4); ++index;
	drawOctafaceVertex(index, 8, 11, 10, 2, 1, 4, 5, 7); ++index;
	drawOctafaceVertex(index, 12, 0, 2, 10, 9, 15, 16, 13); ++index;
	drawOctafaceVertex(index, 14, 13, 16, 17, 22, 23, 20, 19); ++index;
	drawOctafaceVertex(index, 17, 15, 9, 11, 8, 6, 21, 22); ++index;
	drawOctafaceVertex(index, 5, 3, 18, 20, 23, 21, 6, 7); ++index;

	drawTrifaceVertex(index, 0, 1, 2); ++index;
	drawTrifaceVertex(index, 3, 5, 4); ++index;
	drawTrifaceVertex(index, 6, 8, 7); ++index;
	drawTrifaceVertex(index, 11, 9, 10); ++index;
	drawTrifaceVertex(index, 14, 12, 13); ++index;
	drawTrifaceVertex(index, 16, 15, 17); ++index;
	drawTrifaceVertex(index, 22, 21, 23); ++index;
	drawTrifaceVertex(index, 18, 19, 20); 
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
}
void myKeyboard(unsigned char aKey, int aX, int aY) {
    switch(aKey) {
	case '+':
		camera_dis += 0.1;
		changeCameraPos();
		break;
	case '-':
		camera_dis -= 0.1;
		changeCameraPos();
		break;
	case 'v':
	case 'V':
		b4View = !b4View;
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
void drawSquare(int aX, int aZ) {
	glColor3f (0, 0, 0);
	glBegin(GL_POLYGON);
		glVertex3d(-4 + aX, -5 + aZ, 0);
		glVertex3d(-3 + aX, -5 + aZ, 0);
		glVertex3d(-3 + aX, -4 + aZ, 0);
		glVertex3d(-4 + aX, -4 + aZ, 0);
	glEnd();
}
void drawFloor() {
	glPushMatrix();	
		glRotated(-90, 1, 0, 0);
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		for (int i = 0; i < 10; i += 2) 
			for (int j = 0; j < 10; j += 2) {
					drawSquare(i,j);
					drawSquare(i-1,j+1);
			}
		glPopMatrix();
}
void drawSphere() {
	glPushMatrix();		
		//////////////////////////////////
		glTranslated(sphere._slideX, sphere._slideY, sphere._slideZ);
		if (bWireFrame)
			sphere.DrawWireframe();
		else
			sphere.DrawVarityColor();
	glPopMatrix();
}
void drawDodecahedron() {
	glPushMatrix();		
		//////////////////////////////////
		glTranslated(dodecahedron._slideX, dodecahedron._slideY, dodecahedron._slideZ);
		if (bWireFrame)
			dodecahedron.DrawWireframe();
		else
			dodecahedron.DrawVarityColor();
	glPopMatrix();
}
void drawIcosahedron() {
	glPushMatrix();		
		//////////////////////////////////
		glTranslated(icosahedron._slideX, icosahedron._slideY, icosahedron._slideZ);
		if (bWireFrame) icosahedron.DrawWireframe();
		else icosahedron.DrawVarityColor();
	glPopMatrix();
}
void drawTruncatedCube() {
	glPushMatrix();		
		//////////////////////////////////
		glTranslated(truncatedCube._slideX, truncatedCube._slideY, truncatedCube._slideZ);
		if (bWireFrame) truncatedCube.DrawWireframe();
		else truncatedCube.DrawVarityColor();
	glPopMatrix();
}
void drawCylinderBase() {
	glPushMatrix();		
		glRotated(cylBaseRotateAngle, 0, 1, 0);
		//////////////////////////////////
		glTranslated(0, cylBase._slideY, 0);
		if (bWireFrame) cylBase.DrawWireframe();
		else cylBase.DrawColor();
	glPopMatrix();
}
void drawCuboidBase() {
	glPushMatrix();		
		glRotated(cylBaseRotateAngle, 0, 1, 0);
		//////////////////////////////////
		glTranslated(0, cuboidBase._slideY, 0);
		if (bWireFrame) cuboidBase.DrawWireframe();
		else cuboidBase.DrawColor();
	glPopMatrix();
}
void drawCylAxis() {
	glPushMatrix();		
		glRotated(cylBaseRotateAngle, 0, 1, 0);
		//////////////////////////////////
		glTranslated(0, cylAxis._slideY, cylAxis._slideZ);
		glRotatef(90, 1, 0, 0);
		glRotated(cylAxisRotateAngle, 0, 1, 0);
		if (bWireFrame) cylAxis.DrawWireframe();
		else cylAxis.DrawColor();
	glPopMatrix();
}
void drawUAxis() {
	glPushMatrix();	
		glRotated(cylBaseRotateAngle, 0, 1, 0);
		//////////////////////////////////
		glTranslated(0, USupporter._slideY, USupporter._slideZ);
		glRotated(cylAxisRotateAngle, 0, 0, 1);
		if (bWireFrame) USupporter.DrawWireframe();
		else USupporter.DrawColor();
	glPopMatrix();
}
void drawCuboidAxis() {
	glPushMatrix();	
		glRotated(cylBaseRotateAngle, 0, 1, 0);
		//////////////////////////////////
		glTranslated(0, cuboidAxis._slideY, cuboidAxis._slideZ);
		glRotatef(90, 0, 0, 1);
		glRotated(cylAxisRotateAngle, 0, 0, 1);
		glRotated(cylPlateRotateAngle, 0, 1, 0);
		if (bWireFrame) cuboidAxis.DrawWireframe();
		else cuboidAxis.DrawColor();
	glPopMatrix();
}
void drawCylPlate() {
	glPushMatrix();
		glRotated(cylBaseRotateAngle, 0, 1, 0);
		/////////////////////////////////
		glTranslated(0, cylDisk._slideY, cylDisk._slideZ);
		glRotatef(90, 0, 0, 1);
		glRotated(cylAxisRotateAngle, 0, 0, 1);
		glRotated(cylPlateRotateAngle, 0, 1, 0);
		if (bWireFrame) cylDisk.DrawWireframe();
		else cylDisk.DrawColor();
	glPopMatrix();
}
void drawSphere1InDisk() {
	glPushMatrix();
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
		else sphere1InDisk.DrawColor();
	glPopMatrix();
}
void drawSphere2InDisk() {
	glPushMatrix();
		glRotated(cylBaseRotateAngle, 0, 1, 0);
		/////////////////////////////////
		glTranslated(0, sphere2InDisk._slideY, sphere2InDisk._slideZ);

		float r = 0.4;
		float y = r * cos(DEG2RAD * -cylAxisRotateAngle) * sin(DEG2RAD * (240 + cylPlateRotateAngle));
		float x = r * sin(DEG2RAD * -cylAxisRotateAngle) * sin(DEG2RAD * (240 + cylPlateRotateAngle));
		float z = r * cos(DEG2RAD * -(240 + cylPlateRotateAngle));

		glTranslated(x, y, z);
		
		if (bWireFrame) sphere2InDisk.DrawWireframe();
		else sphere2InDisk.DrawColor();
	glPopMatrix();
}
void drawSphere3InDisk() {
	glPushMatrix();
		glRotated(cylBaseRotateAngle, 0, 1, 0);
		/////////////////////////////////
		glTranslated(0, sphere3InDisk._slideY, sphere3InDisk._slideZ);

		float r = 0.4;
		float y = r * cos(DEG2RAD * -cylAxisRotateAngle) * sin(DEG2RAD * (120 + cylPlateRotateAngle));
		float x = r * sin(DEG2RAD * -cylAxisRotateAngle) * sin(DEG2RAD * (120 + cylPlateRotateAngle));
		float z = r * cos(DEG2RAD * -(120 + cylPlateRotateAngle));

		glTranslated(x, y, z);

		if (bWireFrame) sphere3InDisk.DrawWireframe();
		else sphere3InDisk.DrawColor();

	glPopMatrix();
}
void DisplayOneView(int aNType, int aLeft, int aRight, int aTop, int aBottom) {
	mySetupCameraVolume(aNType);
	glViewport(aLeft, aTop, aRight - aLeft, aBottom - aTop);

	changeCameraPos(); ///////////////////

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
	
	/////////////////////////////////////////////////
	drawFloor();
	drawSphere();
	drawDodecahedron();
	drawIcosahedron();
	drawTruncatedCube();

	drawCylinderBase();
	drawCuboidBase();
	drawCylAxis();
	drawUAxis();
	drawCuboidAxis();
	drawCylPlate();
	drawSphere1InDisk();
	drawSphere2InDisk();
	drawSphere3InDisk();
	
}
void myDisplay() {
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	

	if (b4View == false) {
		DisplayOneView(4, 0, screenWidth, 0, screenHeight);
	}
	else {
		DisplayOneView(1, 0, screenWidth / 2, 0, screenHeight / 2);
		DisplayOneView(2, 0, screenWidth / 2, screenHeight / 4, 3 * screenHeight / 4);
		DisplayOneView(3, screenWidth / 2, screenWidth, screenHeight / 4, 3 * screenHeight / 4);
		DisplayOneView(4, screenWidth / 2, screenWidth, 0, screenHeight / 2);
	}
	glFlush();
    glutSwapBuffers();
}
void myInit() {
	float fHalfSize = 5;
	camera_angle = 90.0;
	camera_height = 2.0;
	camera_dis = 11;

	glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    glColor3f(0.0f, 0.0f, 0.0f);

	glFrontFace(GL_CCW);
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(-fHalfSize, fHalfSize, -fHalfSize, fHalfSize, -1000, 1000);

	glMatrixMode(GL_MODELVIEW);
}
int _tmain(int argc, _TCHAR* argv[]) {
	cout << "1, 2: Rotate the base" << endl;
	cout << "3, 4: Rotate axes 1 (cylinder axes)" << endl;
	cout << "5, 6: Rotate axes 2 (cuboid axes)" << endl;
	cout << "W, w: Switch between wireframe and solid mode" << endl;
	cout << "V, v: to switch between 1 and 4 views." << endl;
	cout << "+   : to increase camera distance." << endl;
	cout << "-   : to decrease camera distance." << endl;
	cout << "up arrow  : to increase camera height." << endl;
	cout << "down arrow: to decrease camera height." << endl;
	cout << "<-        : to rotate camera clockwise." << endl;
	cout << "->        : to rotate camera counterclockwise." << endl;

	glutInit(&argc, (char**)argv); //initialize the tool kit
	glutInitDisplayMode(GLUT_DOUBLE |GLUT_RGB);//set the display mode
	glutInitWindowSize(screenWidth, screenHeight); //set window size
	glutInitWindowPosition(100, 100); // set window position on screen
	glutCreateWindow("Assignment1 - Huynh Tan Phuc (51202787)"); // open the screen window

	cylBase.CreateCylinder(20, cylBaseHeight, cylBaseRadius);
	cylBase._slideY = YPlanePos + cylBaseHeight / 2.0;
	cylBase.SetColor(1);

	cuboidBase.CreateCuboid(cuboidBaseSizeXZ / 2.0, cuboidBaseSizeY / 2.0, cuboidBaseSizeXZ / 2.0);
	cuboidBase._slideY = YPlanePos + cylBaseHeight + cuboidBaseSizeY / 2.0;
	cuboidBase.SetColor(0);

	cylAxis.CreateCylinder(20, cylAxisHeight, cylAxisRadius);
	cylAxis._slideY = cylBaseHeight + cuboidBaseSizeY - cylAxisOffset;
	cylAxis._slideZ = cylAxisHeight / 2 + cuboidBaseSizeXZ / 2.0;
	cylAxis.SetColor(2);

	USupporter.CreateUShape(UShapeX / 2.0, UShapeY / 2.0, UShapeZ / 2.0, UShapeThick);
	USupporter._slideY = cylBaseHeight + cuboidBaseSizeY - cylAxisOffset;
	USupporter._slideZ = UShapeZ/2 + UShapeThick + cylAxisHeight + cuboidBaseSizeXZ / 2.0;
	USupporter.SetColor(3);

	cuboidAxis.CreateCuboid(cuboidAxisXZ / 2.0, cuboidAxisY / 2.0, cuboidAxisXZ / 2.0);
	cuboidAxis._slideY = cylBaseHeight + cuboidBaseSizeY - cylAxisOffset;
	cuboidAxis._slideZ = UShapeZ / 2 + 0.4 + UShapeThick + cylAxisHeight + cuboidBaseSizeXZ / 2.0;
	cuboidAxis.SetColor(4);

	cylDisk.CreateCylinder(20, cylPlateHeight, cylPlateRadius);
	cylDisk._slideY = cylBaseHeight + cuboidBaseSizeY - cylAxisOffset;
	cylDisk._slideZ = UShapeZ / 2 + 0.4 + UShapeThick + cylAxisHeight + cuboidBaseSizeXZ / 2.0;
	cylDisk.SetColor(5);

	sphere1InDisk.CreateSphere(10, 15, sphereRadius);
	sphere1InDisk._slideY = cylBaseHeight+cuboidBaseSizeY - cylAxisOffset;
	sphere1InDisk._slideZ = UShapeZ / 2 + 0.4 + UShapeThick + cylAxisHeight + cuboidBaseSizeXZ / 2.0;
	sphere1InDisk.SetColor(6);

	sphere2InDisk.CreateSphere(10, 15, sphereRadius);
	sphere2InDisk._rotateX = 0.4 * cos(PI / 180 * 30.0 + cylPlateRotateAngle / 180.0 * PI);
	sphere2InDisk._slideX = 0.4 * cos(PI / 180 * 30.0 + cylPlateRotateAngle / 180.0 * PI);
	sphere2InDisk._slideY = cylBaseHeight + cuboidBaseSizeY - cylAxisOffset;
	sphere2InDisk._slideZ = UShapeZ / 2 + 0.4 + UShapeThick + cylAxisHeight + cuboidBaseSizeXZ / 2.0;
	sphere2InDisk.SetColor(2);

	sphere3InDisk.CreateSphere(10, 15, sphereRadius);
	sphere3InDisk._slideY = cylBaseHeight + cuboidBaseSizeY - cylAxisOffset;
	sphere3InDisk._slideZ = UShapeZ / 2 + 0.4 + UShapeThick + cylAxisHeight + cuboidBaseSizeXZ / 2.0;
	sphere3InDisk.SetColor(0);

	sphere.CreateSphere(7, 10, sphereShapeRadius);
	sphere._slideX = cylBaseHeight + 3.0;
	sphere._slideY = sphereShapeRadius;
	sphere._slideZ = -3;
	sphere.SetColor(0);

	dodecahedron.CreateDodecahedron(dodecahedronRadius);
	dodecahedron._slideX = -(cylBaseHeight + 3.0);
	dodecahedron._slideY = dodecahedronRadius;
	dodecahedron._slideZ = 3;
	dodecahedron.SetColor(0);

	icosahedron.CreateIcosahedron(icosahedronRadius);
	icosahedron._slideX = cylBaseHeight + 3.0;
	icosahedron._slideY = icosahedronRadius;
	icosahedron._slideZ = 3;
	icosahedron.SetColor(0);

	truncatedCube.CreateTruncatedCube(truncatedCubeSize, truncatedCubeCut);
	truncatedCube._slideX = -(cylBaseHeight + 3.0);
	truncatedCube._slideY = truncatedCubeSize;
	truncatedCube._slideZ = -3;
	truncatedCube.SetColor(0);

	myInit();

	glutDisplayFunc(myDisplay);
	glutTimerFunc(100, onTimer, 0);
	glutKeyboardFunc(myKeyboard);
    glutSpecialFunc(mySpecialKeyboard);
	
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


