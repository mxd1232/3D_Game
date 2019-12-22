package shaders;

import entities.Camera;
import entities.Light;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import toolbox.Maths;

import java.util.List;

public class TerrainShader extends ShaderProgram {

    private static final int MAX_LIGHTS =4;

    private static final String VERTEX_FILE = "src/main/java/Shaders/terrainVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/main/java/Shaders/terrainFragmentShader.txt";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition[];
    private int location_lightColor[];
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_skyColor;
    private int location_backgroundTexture;
    private int location_rTexture;
    private int location_gTexture;
    private int location_bTexture;
    private int location_blendMap;

    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }


    @Override
    protected void getAllUniformLocations() {

        location_transformationMatrix= super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_skyColor = super.getUniformLocation("skyColor");
        location_backgroundTexture =super.getUniformLocation("backgroundTexture");
        location_rTexture =super.getUniformLocation("rTexture");
        location_gTexture =super.getUniformLocation("gTexture");
        location_bTexture =super.getUniformLocation("bTexture");
        location_blendMap =super.getUniformLocation("blendMap");

        location_lightPosition = new int[MAX_LIGHTS];
        location_lightColor = new int[MAX_LIGHTS];
        for(int i=0;i<MAX_LIGHTS;i++)
        {
            location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
            location_lightColor[i] = super.getUniformLocation("lightColor[" + i + "]");

        }
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0,"position");
        super.bindAttribute(1,"textureCoords");
        super.bindAttribute(2,"normal");
    }

    public void connectTextureUnits()
    {
        super.loadInt(location_backgroundTexture,0);
        super.loadInt(location_rTexture,1);
        super.loadInt(location_gTexture,2);
        super.loadInt(location_bTexture,3);
        super.loadInt(location_blendMap,4);
    }


    public void loadSkyColor(float r, float g, float b)
    {
        super.loadVector(location_skyColor,new Vector3f(r,g,b));
    }

    public void loadShineVariables(float damper,float reflection)
    {
        super.loadFloat(location_shineDamper,damper);
        super.loadFloat(location_reflectivity,reflection);
    }

    public void loadLights(List<Light> lights)
    {
        for(int i=0;i<MAX_LIGHTS;i++)
        {
            if(i<lights.size())
            {
                super.loadVector(location_lightPosition[i],lights.get(i).getPosition());
                super.loadVector(location_lightColor[i],lights.get(i).getColor());
            }else {
                super.loadVector(location_lightPosition[i],new Vector3f(0,0,0));
                super.loadVector(location_lightColor[i],new Vector3f(0,0,0));
            }
        }


    }

    public void loadTransformationMatrix(Matrix4f matrix)
    {
        super.loadMatrix(location_transformationMatrix,matrix);
    }
    public void loadProjectionMatrix(Matrix4f projection)
    {
        super.loadMatrix(location_projectionMatrix,projection);
    }
    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix,viewMatrix);
    }
}
