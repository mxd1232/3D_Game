package textures;

public class ModelTexture {



    private int textureID;

    private float shineDamper = 1;
    private float reflectivity = 0;
    private boolean hasTransparency=false;
    private boolean usFakeLighting = false;



    private int numberOfRows =1;


    public boolean isUsFakeLighting() {
        return usFakeLighting;
    }

    public void setUsFakeLighting(boolean usFakeLighting) {
        this.usFakeLighting = usFakeLighting;
    }



    public boolean isHasTransparency() {
        return hasTransparency;
    }

    public void setHasTransparency(boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
    }



    public ModelTexture(int id){
        this.textureID=id;
    }
    public int getID(){
        return this.textureID;
    }
    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
}
