package c2g2.engine.graph;

import java.io.BufferedReader;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.joml.Vector2f;
import org.joml.Vector3f;
import java.io.File;
import java.util.ArrayList;

public class OBJLoader {
    public static Mesh loadMesh(String fileName) throws Exception {
    	//// --- student code ---

        //use arraylist first and convert to array later
        ArrayList<Float> p = new ArrayList<Float>();
        ArrayList<Float> t = new ArrayList<Float>();
        ArrayList<Float> n = new ArrayList<Float>();
        ArrayList<Integer> ind = new ArrayList<Integer>();
        
        //path to obj file
        File file = new File("/Users/cheng/Documents/workspace/pa1/pa1_release/"+fileName);
        System.out.println("path = " + file);
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
        	
        	String line;
        	//while loop reading 1 line at a time
        	while((line = br.readLine() ) != null){
        		
        		if(line.startsWith("#")){
    				continue;
    			}
        		if(line.startsWith("v ")){
    				Scanner sc = new Scanner(line);
    				sc.next(); //skip "v"
    				p.add(sc.nextFloat());
    				p.add(sc.nextFloat());
    				p.add(sc.nextFloat());
        		}

        		/*
        		if(line.startsWith("vt ")){
    				Scanner sc = new Scanner(line);
    				sc.next();
    				t.add(sc.nextFloat());
    				t.add(sc.nextFloat());
    				t.add(sc.nextFloat());
        		}
				*/
        		
        		if(line.startsWith("vn ")){
    				Scanner sc = new Scanner(line);
    				sc.next();
    				n.add(sc.nextFloat());
    				n.add(sc.nextFloat());
    				n.add(sc.nextFloat());
        		}
        		
        		if(line.startsWith("f ")){
        			
        			//in cases such as bunny.obj
        			if(line.contains("/")){
            			String[] f1 = line.split(" ");
            			String[] f1x = f1[1].split("/");
	    				String[] f1y = f1[2].split("/");
	    				String[] f1z = f1[3].split("/");
        				
	    				ind.add(Integer.parseInt(f1x[0])-1);
	        			ind.add(Integer.parseInt(f1y[0])-1);
	        			ind.add(Integer.parseInt(f1z[0])-1);
        			}
        			//else in cases with just 3 integers such as cube
        			else{
	    				Scanner sc = new Scanner(line);
	    				sc.next();
	    				ind.add(sc.nextInt()-1);
	    				ind.add(sc.nextInt()-1);
	    				ind.add(sc.nextInt()-1);
        			}
        		}
        	}
        }

        //add {0,0} and {0,0,0} to texture and normal arrays respectively
        //so they wont be empty if file does not have any vt/vn coordinates
    	Float x = 0f;
        if(t.size() == 0){
        	t.add(x);
        	t.add(x);
        }
        if(n.size() == 0){
        	n.add(x);
        	n.add(x);
        	n.add(x);
        }
        
        //convert arraylists into array to pass into mesh
        float[] positions = new float[p.size()];
        float[] textCoords = new float[t.size()];
        float[] norms = new float[n.size()];
        int[] indices = new int[ind.size()];

        for(int i = 0; i < p.size(); i++) {
            if (p.get(i) != null) {
                positions[i] = p.get(i);
            }
        }
        for(int i = 0; i < t.size(); i++) {
            if (t.get(i) != null) {
            	textCoords[i] = t.get(i);
            }
        }
        for(int i = 0; i < n.size(); i++) {
            if (n.get(i) != null) {
            	norms[i] = n.get(i);
            }
        }
        for(int i = 0; i < ind.size(); i++) {
            if (ind.get(i) != null) {
                indices[i] = ind.get(i);
            }
        }        

        //your task is to read data from an .obj file and fill in those arrays.
        //the data in those arrays should use following format.
        //positions[0]=v[0].position.x positions[1]=v[0].position.y positions[2]=v[0].position.z positions[3]=v[1].position.x ...
        //textCoords[0]=v[0].texture_coordinates.x textCoords[1]=v[0].texture_coordinates.y textCoords[2]=v[1].texture_coordinates.x ...
        //norms[0]=v[0].normals.x norms[1]=v[0].normals.y norms[2]=v[0].normals.z norms[3]=v[1].normals.x...
        //indices[0]=face[0].ind[0] indices[1]=face[0].ind[1] indices[2]=face[0].ind[2] indices[3]=face[1].ind[0]...(assuming all the faces are triangle face)
        return new Mesh(positions, textCoords, norms, indices);
    }

}
