package jbomberman.game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class ActionSerializer {
	
	public static byte[] serialize(Action action) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		byte[] actionBytes = null;
		try {
		  out = new ObjectOutputStream(bos);   
		  out.writeObject(action);
		  actionBytes = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		  try {
		    if (out != null) {
		      out.close();
		    }
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		  try {
		    bos.close();
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		}
		return actionBytes;
	}
	
	public static Action deserialize(byte[] data) {
		Action a = null;
		if(data != null) {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			ObjectInput in = null;
			try {
			  in = new ObjectInputStream(bis);
			  a = (Action)in.readObject(); 
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
			  try {
			    bis.close();
			  } catch (IOException ex) {
			    // ignore close exception
			  }
			  try {
			    if (in != null) {
			      in.close();
			    }
			  } catch (IOException ex) {
			    // ignore close exception
			  }
			}
		}
		return a;
	}

}
