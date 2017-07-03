package pt.up.yap.app;

import android.content.Context;
import android.content.res.AssetManager;

import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by vsc on 30/05/16.
 */

public class CreateFiles {

    public static void deleteFolder(File folder) {

        if (folder.exists()) {
            File[] files = folder.listFiles();
            if (files != null) { //some JVMs return null for empty dirs
                for (File f : files) {
                    if (f.isDirectory()) {
                        deleteFolder(f);
                    } else {
                        f.delete();
                    }
                }
            }
            folder.delete();
        }
    }

    public static void deleteByName(String name){
        try {
            File dir = new File(android.os.Environment.getExternalStorageDirectory() + name);
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    new File(dir, children[i]).delete();
                }
            }
        }catch (Exception e){
            Log.i("erro", "deleteByName: "+e.toString());
        }
    }


    public static void setupfiles(Context context, AssetManager assets){
        try {
            File location= context.getFilesDir();
            //deleteFolder(new File(location, "Yap"));
            deleteByName("Yap");
            new File(location, "Yap/pl").mkdirs();
            new File(location, "Yap/os").mkdirs();

//            deleteFolder(context.getExternalFilesDir("Yap"));
//            context.getExternalFilesDir("Yap").mkdirs();
//            context.getExternalFilesDir("Yap/pl").mkdirs();
//            context.getExternalFilesDir("Yap/os").mkdirs();
            String list[] = {};
            list= assets.list("Yap");
            for (int i = 0; i < list.length; i++) {
                copy(context, "Yap/"+ list[i]);
            }
            list = null;
            list= assets.list("Yap/pl");
            for (int i = 0; i < list.length; i++) {
                copy(context, "Yap/pl/"+ list[i]);
            }
            list =null;
            list= assets.list("Yap/os");
            for (int i = 0; i < list.length; i++) {
                copy(context, "Yap/os/"+ list[i]);
            }
            Log.d("CreateFiles", context.getFilesDir().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copy(Context context,  String originalName)  {
        try {
            if (originalName.contains(".")) {
                File outFile;

                outFile = new File(context.getFilesDir(), originalName );

                InputStream in = context.getAssets().open(originalName);
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFile));


                byte data[] = new byte[1024];
                int count;

                while ((count = in.read(data)) != -1) {
                    out.write(data, 0, count);
                }

                out.flush();
                out.close();
                in.close();

                in = null;
                out = null;
                //Log.d("Copied: ", originalName + " to " + destinationName);
                //System.out.println("Copied: "+ originalName + " to " + destinationName);
                //System.out.println();
            }
            }catch(IOException e){
                e.printStackTrace();
            }

    }
}
