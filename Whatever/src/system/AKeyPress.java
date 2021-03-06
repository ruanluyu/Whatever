package system;

public class AKeyPress implements NeedUpdate{

	  public boolean keyPress[] = { false, false, false, false, false };
	  public char id[] = { ' ', 'w', 's', 'a', 'd' };
	  public boolean firstA = false;
	  public boolean firstD = false;
	  public GameManager gm;
	  
	  public AKeyPress(GameManager g){
		  gm = g;
	  }
	  
	  @Override
	  public void update(){
		  checkAD();
	  }
	  
	  public void checkAD() {
	    if (keyPress[3] && keyPress[4] == false) {
	      firstA = true;
	      firstD = false;
	    } else if (keyPress[3] == false && keyPress[4]) {
	      firstA = false;
	      firstD = true;
	    } else if (keyPress[3] == false && keyPress[4] == false) {
	      firstA = false;
	      firstD = false;
	    }
	  }

	  public boolean keyBlank() {
	    return keyPress[0];
	  }

	  public boolean keyW() {
	    return keyPress[1];
	  }

	  public boolean keyS() {
	    return keyPress[2];
	  }

	  public boolean keyA() {
	    boolean out = false;
	    if (keyPress[3]) {
	      out = true;
	    }
	    if (firstA && keyPress[3] && keyPress[4]) {
	      out = false;
	    }
	    return out;
	  }
	  

	  public boolean keyD() {
	    boolean out = false;
	    if (keyPress[4]) {
	      out = true;
	    }
	    if (firstD && keyPress[3] && keyPress[4]) {
	      out = false;
	    }
	    return out;
	  }

	  public void checkKeyPressed() {
	    for (int i = 0; i < 5; i++) {
	      if (gm.parent.key == id[i]) {
	        keyPress[i] = true;
	      }
	    }
	  }

	  public void checkKeyRelease() {
	    for (int i = 0; i < 5; i++) {
	      if (gm.parent.key == id[i]) {
	        keyPress[i] = false;
	      }
	    }
	  }

	public void init() {
		for(int i = 0;i<keyPress.length;i++){
			keyPress[i] = false;
		}
		firstA = false;
		firstD = false;
	}

	}