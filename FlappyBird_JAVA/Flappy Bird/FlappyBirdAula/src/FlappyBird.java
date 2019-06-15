import java.util.ArrayList;
import java.util.Random;

public class FlappyBird implements Jogo{
	
	public double ground_offset = 0;
	public double gvx = 70;
	public Passaro passaro;
	public ArrayList<Cano> canos = new ArrayList<Cano>();
	public Random gerador = new Random();
	public Timer timer_cano;
	
	public FlappyBird(){
		 passaro = new Passaro(35,(getLargura()-112)/2 + 24/2);
		 timer_cano = new Timer(3, true, addCano());
		 addCano().executa();
	}
	
	private Acao addCano(){
        return new Acao(){
          public void executa(){
        	  canos.add(new Cano(getLargura()+10, gerador.nextInt(getAltura()-112-Cano.HOLESIZE), -gvx));
          }
        };
      }
	
	public String getTitulo(){
		return "Flappy Bird Aula";
	}
	public int getLargura(){
		return 384;
	}
    public int getAltura(){
    	return 512;
    }

    public void tecla(String tecla){
    	if(tecla.equals(" ")){
    		passaro.flap();
    	}
    }
    
    public void tique(java.util.Set<String> teclas, double dt){
    	ground_offset += dt*gvx;
    	ground_offset = ground_offset%308;
    	
    	timer_cano.tique(dt);
    	
    	passaro.atualiza(dt);
    	if(passaro.y+24>=getAltura()-112){
    		System.out.println("GAME OVER BOYZ");
    	} else if(passaro.y <= 0){
    		System.out.println("GAME OVER BOYZ");
    	}
    	
    	for(Cano cano: canos){
    		cano.atualiza(dt);
    		if(passaro.box.intersecao(cano.boxcima)!=0 || passaro.box.intersecao(cano.boxbaixo)!=0){
    			System.out.println("GAME OVER BOYZ");
    		}
    	}
    	
    	if(canos.size()>0 && canos.get(0).x < -60){
    		canos.remove(0);
    		System.out.println("Cano Removido");
    		System.out.println(canos.size());
    	}
    	
    	
    }
    
    public void desenhar(Tela t){
    	//Background
    	t.imagem("flappy.png", 0, 0, 288, 512, 0, 0, 0);
    	t.imagem("flappy.png", 0, 0, 288, 512, 0, 288, 0);
    	//tela.imagem("flappy.png", 0, 0, 288, 512, 0, 288*2, 0);
    	
    	for(Cano cano: canos){
    		cano.desenha(t);
    	}
    	
    	//Ground
    	t.imagem("flappy.png", 292, 0, 308, 112, 0, -ground_offset, getAltura()-112);
    	t.imagem("flappy.png", 292, 0, 308, 112, 0, 308 -ground_offset, getAltura()-112);
    	t.imagem("flappy.png", 292, 0, 308, 112, 0, 308*2 -ground_offset, getAltura()-112);
    	
    	
    	
    	passaro.desenhar(t);
    	
    }
    
    public static void main(String[] args) {
        roda();
    }

    private static void roda() {
    	new Motor(new FlappyBird());
    }
}
