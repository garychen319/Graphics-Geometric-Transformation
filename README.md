Graphics - Geometric Transformation

Platform used - LWJGL3

Included 3 extra obj files in models folder and modified DummyGame.Java so it runs with the 3 obj filenames. Obj source: http://tf3dm.com/.

The code can be ran by running build.xml

Geometric transformation. OBJLoader method loadMesh returns an instance of the class Mesh. Methods implemented:

1. translate(Vector3fc tran) Translate the mesh by a given vector.

2. scale(float sx, float sy, float sz) Scale the mesh by sx, sy, and sz along the x-, y-, and z- direction,
respectively.

3. rotate(Vector3fc axis, float angle) Rotate the mesh around the given axis by the degree angle. The rotation angle is given in degrees.
2/4
  
4. reflect(Vector3fc p, Vector3fc n) Reflect the mesh with respect to the given plane. The plane is specified by the equation
( x ̃   p ̃ ) · n~ = 0 .
