package com.application.sed.raid_tracker_appli;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.MapQuestRoadManager;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.bonuspack.routing.RoadNode;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;

public class CreateParcours extends Activity implements MapEventsReceiver {
    MapView map = null;
    private ArrayList<GeoPoint> ListGeoPoint = new ArrayList<>();
    int ParcoursListGeoPoint = 0;
    MyLocationNewOverlay mLocationOverlay;

    Marker standardmarker; // = new Marker(map);
    Marker standardmarker1; // = new Marker(map);
    Marker standardmarker2;

    int numbouton = 0;

    public static ArrayList<List> Liste =new ArrayList<>();


    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //handle permissions first, before map is created. not depicted here TODO

        //load/initialize the osmdroid configuration, this can be done
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's tile servers will get you banned based on this string

        //inflate and create the map
        setContentView(R.layout.activity_create_parcours);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);


        //positionnement lors de l'ouverture de la carte
        IMapController mapController = map.getController();
        mapController.setZoom(9);
        final GeoPoint startPoint = new GeoPoint(48.732084,-3.4591440000000375);
        mapController.setCenter(startPoint);

        //géolocaliser l'appareil
        MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getApplicationContext()), map);
        mLocationOverlay.enableMyLocation();
        map.setMultiTouchControls(true);
        map.getOverlays().add(mLocationOverlay);
        this.mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(ctx),map);
        this.mLocationOverlay.enableMyLocation();
        map.getOverlays().add(this.mLocationOverlay);

        // ajouter l'echelle
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(map);
        map.getOverlays().add(myScaleBarOverlay);

        // ajouter boussolle
        CompassOverlay mCompassOverlay = new CompassOverlay(getApplicationContext(), new InternalCompassOrientationProvider(getApplicationContext()), map);
        mCompassOverlay.enableCompass();
        map.getOverlays().add(mCompassOverlay);

        //créer un road manager (Appel vers l'api pour guider d'un point à un autre
//        RoadManager roadManager = new MapQuestRoadManager("o7gFRAppOrsTtcBhEVYrY6L7AGRtXldE");

        // fixer le point de départ et le point d'arrivée
       // ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        //waypoints.add(startPoint);
        //GeoPoint endPoint = new GeoPoint(48.4, -1.9);
        //waypoints.add(endPoint);


        //choisir le type de route
        //roadManager.addRequestOption("routeType=pedestrian");
        // Road road = roadManager.getRoad(waypoints);

        //créer les lignes
        // Polyline roadOverlay = RoadManager.buildRoadOverlay(road);
        //map.getOverlays().add(roadOverlay);

        // permet de mettre à jour la carte
        //map.invalidate();


        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this, this);
        map.getOverlays().add(0, mapEventsOverlay);



        //récupère les id des boutons
        ImageButton greenflag = (ImageButton) findViewById(R.id.greenflag);
        ImageButton redflag = (ImageButton) findViewById(R.id.redflag);
        ImageButton poi = (ImageButton) findViewById(R.id.poi);




        //action listener sur le drapeau de depart
        greenflag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.info("coucou", "ouiii");
                standardmarker = new Marker(map);
                numbouton = 1;
                standardmarker.setIcon(getResources().getDrawable(R.drawable.green_flag));


                //map.getOverlays().add(standardmarker);

            }
        });


        //action listenner sur le drapeau d'arrivée

        redflag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.info("coucou", "rouuuuge");
                standardmarker1 = new Marker(map);
                numbouton = 2;
                standardmarker1.setIcon(getResources().getDrawable(R.drawable.red_flag));
                //map.getOverlays().add(standardmarker1);
            }
        });

        //action listener sur les points de passage
        redflag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.info("coucou", "rouuuuge");
                standardmarker1 = new Marker(map);
                numbouton = 2;
                standardmarker1.setIcon(getResources().getDrawable(R.drawable.red_flag));
                //map.getOverlays().add(standardmarker1);
            }
        });

        //action listener sur les points de passage
        poi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.info("coucou", "bleuuu");
                standardmarker2 = new Marker(map);
                numbouton = 3;
                standardmarker2.setIcon(getResources().getDrawable(R.drawable.passage));
                //map.getOverlays().add(standardmarker1);
            }
        });



        //ajouter marqueur
//        GeoPoint enssatpoint =  new GeoPoint(48.729673,-3.4624261999999817);
//
//        Marker startMarker = new Marker(map);
//        startMarker.setPosition(enssatpoint);
//        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//        String latitude=String.valueOf(enssatpoint.getLatitude());
//        String longitude=String.valueOf(enssatpoint.getLongitude());
//        startMarker.setTitle("ENSSAT"+"\n"+"latitude: "+latitude+'\n'+"longitude: "+longitude);
//
//        //ajouter un icone particuliere
//        startMarker.setIcon(getResources().getDrawable(R.drawable.pointer));
//        map.getOverlays().add(startMarker);

    }

//    public void drawRouteAsync(){
//        GeoPoint pointFrom = new GeoPoint(51.489878, 6.143294);
//        GeoPoint pointTo = new GeoPoint(51.488978, 6.746994);
//
//        new GetRouteTask(getApplicationContext(), this).execute(new GeoPoint(){pointFrom , pointTo});
//
//    }



    @Override public boolean longPressHelper(GeoPoint p) {

        GeoPoint tmpgeo = new GeoPoint(p.getLatitude(),p.getLongitude());
        //Marker startMarker = new Marker(map);
        //startMarker.setPosition(tmpgeo);
        String latitude=String.valueOf(p.getLatitude());
        String longitude=String.valueOf(p.getLongitude());
        switch (numbouton){


            case 0:
                break;
            case 1:
                standardmarker.setPosition(tmpgeo);
                standardmarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);


                Utils.debug("longPressHelper","Lat "+latitude + "long " + longitude);

        //        //Liste de points
        //        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        //
        //        //waypoints.add(startPoint);
        //
        //        //waypoints.add(endPoint);

                standardmarker.setTitle("point de départ"+"\n"+"latitude: "+latitude+'\n'+"longitude: "+longitude);

                //ajouter un icone particuliere
                //startMarker.setIcon(getResources().getDrawable(R.drawable.pointer));
                map.getOverlays().add(standardmarker);
                ListGeoPoint.add(tmpgeo);
                map.invalidate();
                //setRoad();

                setRoad();

                break;
            case 2:
                standardmarker1.setPosition(tmpgeo);
                standardmarker1.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                Utils.debug("longPressHelper","Lat "+latitude + "long " + longitude);

                //        //Liste de points
                //        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
                //
                //        //waypoints.add(startPoint);
                //
                //        //waypoints.add(endPoint);

                standardmarker1.setTitle("point d'arrivée"+"\n"+"latitude: "+latitude+'\n'+"longitude: "+longitude);

                //ajouter un icone particuliere
                //startMarker.setIcon(getResources().getDrawable(R.drawable.pointer));
                map.getOverlays().add(standardmarker1);
                ListGeoPoint.add(tmpgeo);
                map.invalidate();
                //setRoad();

                setRoad();

                break;

            case 3:
                standardmarker2.setPosition(tmpgeo);
                standardmarker2.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                Utils.debug("longPressHelper","Lat "+latitude + "long " + longitude);

                //        //Liste de points
                //        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
                //
                //        //waypoints.add(startPoint);
                //
                //        //waypoints.add(endPoint);

                standardmarker2.setTitle("nouveau point de passage"+"\n"+"latitude: "+latitude+'\n'+"longitude: "+longitude);

                //ajouter un icone particuliere
                //startMarker.setIcon(getResources().getDrawable(R.drawable.pointer));
                map.getOverlays().add(standardmarker2);
                ListGeoPoint.add(tmpgeo);
                map.invalidate();
                setRoad();
                break;

        }


        return false;
    }


    public void setRoad(){

        ArrayList<GeoPoint> parcours = new ArrayList<>();



        if (ParcoursListGeoPoint <1) {
            ParcoursListGeoPoint += 1;
        }
        else {
            for (int i = ParcoursListGeoPoint; i < ListGeoPoint.size(); i++){
                parcours.add(ListGeoPoint.get(i-1));
                parcours.add(ListGeoPoint.get(i));
                RoadManager roadManager = new MapQuestRoadManager("o7gFRAppOrsTtcBhEVYrY6L7AGRtXldE");

                roadManager.addRequestOption("routeType=pedestrian");
                Road road = roadManager.getRoad(parcours);

                Polyline roadOverlay = RoadManager.buildRoadOverlay(road);

                map.getOverlays().add(roadOverlay);

                map.invalidate();

                MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this, this);

                map.getOverlays().add(0, mapEventsOverlay);
                ParcoursListGeoPoint += 1;

            }
        }
    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
        Utils.debug("SingleTapConfirmedHelped", "Je rentre ici");
        return false;
    }



    public void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void geolocateme (View view){
//        this.mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(view.getContext()),map);
//        this.mLocationOverlay.enableMyLocation();
//        map.getOverlays().add(this.mLocationOverlay);
//
//        GeoPoint myPoint = new GeoPoint(this.mLocationOverlay.getMyLocation().getLatitude(),this.mLocationOverlay.getMyLocation().getLongitude());
//        map.getController().setCenter(myPoint);
//        map.getController().setZoom(14.0);




    }

    public void onPause(){
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }



}