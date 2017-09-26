package c2g2.engine.graph;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import c2g2.engine.GameItem;

public class Transformation {

    private final Matrix4f projectionMatrix;
    
    private final Matrix4f viewMatrix;
    
    private final Matrix4f modelMatrix;

    public Transformation() {
        projectionMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        modelMatrix = new Matrix4f();
    }

    public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
    	
    	//// --- student code ---
        double d_fov = (double) fov;
    	Float f = (float) (1f/(Math.tan(d_fov/2)));
    	Float aspect = width/height;
        //using matrix equation given on piazza
    	Matrix4f projectionMatrix = new Matrix4f(f/aspect, 0, 0, 0, 
    							  0, f, 0, 0, 
    							  0, 0, (zFar+zNear)/(zNear-zFar), -1,
    							  0, 0, (2*zFar*zNear)/(zNear-zFar), 0);
        return projectionMatrix;
        
    }
    
    public Matrix4f getViewMatrix(Camera camera) {
    
    	Vector3f cameraPos = camera.getPosition();
    	Vector3f cameraTarget = camera.getTarget();
    	Vector3f up = camera.getUp();
        viewMatrix.identity();
    	//// --- student code ---
        
        //w = gaze (normal vector from position to target)
        //u = txw / ||txw||
        //v = wxu
        Vector3f w = new Vector3f(cameraPos.x - cameraTarget.x, cameraPos.y - cameraTarget.y, cameraPos.z - cameraTarget.z);
        w.normalize();
        Vector3f u = new Vector3f(0,0,0);
        u.cross(up,w);
        u.normalize();
        Vector3f v = new Vector3f(0,0,0);
        v.cross(w, u);
        
        //view matrix using u, v, w
        Matrix4f viewm = new Matrix4f(u.x, v.x, w.x, 0, u.y, v.y, w.y, 0, u.z, v.y, w.z, 0, 0, 0, 0, 1);
        //multiply by inv to inverse
        Matrix4f inv = new Matrix4f(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, -cameraPos.x, -cameraPos.y, -cameraPos.z, 1);
        viewMatrix.mul(viewm,inv);
        
        return viewMatrix;
        
    }
    
    public Matrix4f getModelMatrix(GameItem gameItem){

        Vector3f rotation = gameItem.getRotation();
        Vector3f position = gameItem.getPosition();
        modelMatrix.identity();
    	//// --- student code ---
        //Declaring variables for Rotation matrix R, casting to float
        float cosa = (float) Math.cos((double) Math.toRadians(rotation.x));
        float cosb = (float) Math.cos((double) Math.toRadians(rotation.y));
        float cosg = (float) Math.cos((double) Math.toRadians(rotation.z));
        float sina = (float) Math.sin((double) Math.toRadians(rotation.x));
        float sinb = (float) Math.sin((double) Math.toRadians(rotation.y));
        float sing = (float) Math.sin((double) Math.toRadians(rotation.z));
        
        //Matrix R for rotation
        Matrix4f R = new Matrix4f(cosb*cosg, cosb*sing, -1*sinb, 0, 
				        	   	  cosg*sina*sinb-cosa*sing, cosa*cosg+sina*sinb*sing, cosb*sina, 0,
				        		  cosa*cosg*sinb+sina*sing, -1*cosg*sina+cosa*sinb*sing, cosa*cosb, 0,
				        		  0, 0, 0, 1);

        //Matrix S for scaling
        Float scale = gameItem.getScale();
        Matrix4f S = new Matrix4f(scale, 0, 0, 0, 
        						  0, scale, 0, 0,
        						  0, 0, scale, 0,
        						  0, 0, 0, 1);
        
        //Matrix T for translation
        Matrix4f T = new Matrix4f(1, 0, 0, 0,
        					      0, 1, 0, 0,
        					      0, 0, 1, 0,
        					      position.x, position.y, position.z, 1);
        
        //Do in order T->R->S
        R.mul(S);
        T.mul(R);
        modelMatrix.mul(T);
        
        return modelMatrix;
    }

    public Matrix4f getModelViewMatrix(GameItem gameItem, Matrix4f viewMatrix) {
        Matrix4f viewCurr = new Matrix4f(viewMatrix);
        return viewCurr.mul(getModelMatrix(gameItem));
    }
}