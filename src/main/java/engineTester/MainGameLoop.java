package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import shaders.StaticShader;
import org.lwjgl.opengl.Display;
import models.RawModel;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static void main(String[] args)
    {
        DisplayManager.createDisplay();
        Loader loader = new Loader();


        Light light = new Light(new Vector3f(0,10000,-70000),new Vector3f(1,1,1));
        List<Light> lights = new ArrayList<>();
        lights.add(light);
        lights.add(new Light(new Vector3f(-200,10,-200),new Vector3f(10,0,0)));
        lights.add(new Light(new Vector3f(200,10,200),new Vector3f(0,0,10)));

        ModelData data = OBJFileLoader.loadOBJ("tree");

        RawModel playerModel = OBJLoader.loadOBJModel("IronMan",loader);
        TexturedModel playerTextured = new TexturedModel(playerModel,new ModelTexture(loader.loadTexture("colorCube")));
        Player player = new Player(playerTextured,new Vector3f(100,0,-50),0,0,0,0.03f);

        Camera camera = new Camera(player);

        List<GuiTexture> guis = new ArrayList<>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("socuwan"),new Vector2f(0.5f,0.5f)
        ,new Vector2f(0.25f,0.25f));
        guis.add(gui);

        GuiRenderer guiRenderer = new GuiRenderer(loader);
        //**********TERAIN********************

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture,rTexture,gTexture,bTexture);

        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

        //************MODELS************************

        RawModel model = loader.loadToVAO(data.getVertices(),data.getTextureCoords(),data.getNormals(),data.getIndices());
        TexturedModel tree = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
       // tree.getTexture().setHasTransparency(true);
       // tree.getTexture().setUsFakeLighting(true);
       // tree.getTexture().setReflectivity(1);
       // tree.getTexture().setShineDamper(15);

        TexturedModel grass = new TexturedModel( OBJLoader.loadOBJModel("grassModel",loader),new ModelTexture(loader.loadTexture("grassTexture")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUsFakeLighting(true);
        TexturedModel fern = new TexturedModel( OBJLoader.loadOBJModel("fern",loader),new ModelTexture(loader.loadTexture("fern")));
        fern.getTexture().setHasTransparency(true);
        fern.getTexture().setUsFakeLighting(true);
        fern.getTexture().setNumberOfRows(2);
        Terrain terrain = new Terrain(0,-1,loader,texturePack,blendMap,"heightmap");
        //Terrain terrain2 = new Terrain(-1,-1,loader,texturePack,blendMap,"heightmap");



        List<Entity> entities = new ArrayList<>();
        Random random = new Random();

        for(int i =0;i<500;i++){

            if(i%10==0) {
                float x = random.nextFloat() * 800 - 400;
                float z = random.nextFloat() * -600;
                entities.add(new Entity(tree, new Vector3f(x, terrain.getHeightOfTerrain(x, z), z),
                        0, 0, 0f, 3f));
            }
       /*     if(i%5==0) {
            float x=random.nextFloat()*800-400;
            float z =random.nextFloat()*-600;
                entities.add(new Entity(grass,new Vector3f( x,terrain.getHeightOfTerrain(x,z),z),
                        0,0,0f,1f));

             }
*/
            if(i%20==0){
                float x=random.nextFloat()*800-400;
                float z =random.nextFloat()*-600;
                entities.add(new Entity(fern,random.nextInt(4),new Vector3f( x,terrain.getHeightOfTerrain(x,z),z),
                        0,0,0f,0.6f));
            }

        }

        MasterRenderer renderer = new MasterRenderer();
        while(!Display.isCloseRequested())
        {
            camera.move();
            player.move(terrain);
            renderer.processEntity(player);

            renderer.processTerrain(terrain);
            //renderer.processTerrain(terrain2);
            for(Entity entity:entities)
            {
                renderer.processEntity(entity);
            }



            renderer.render(lights,camera);
            guiRenderer.render(guis);
            DisplayManager.updateDisplay();
        }
        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
