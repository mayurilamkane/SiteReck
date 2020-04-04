package be.project.sitereck.ProjectManager.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

import be.project.sitereck.R;

public class MapActivity extends AppCompatActivity {
    MapView map = null;
    IMapController mapController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_map);
        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        mapController = map.getController();
        mapController.setZoom(9.5);
        GeoPoint startPoint = new GeoPoint(17.6599, 75.9064);
        mapController.setCenter(startPoint);
        Overlay touchOverlay = new Overlay(this){
            ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay = null;
            @Override
            public void draw(Canvas arg0, MapView arg1, boolean arg2) {

            }
            @Override
            public boolean onSingleTapConfirmed(final MotionEvent e, final MapView mapView) {

                final Drawable marker = getApplicationContext().getResources().getDrawable(R.drawable.ic_marker);
                Projection proj = mapView.getProjection();
                GeoPoint loc = (GeoPoint) proj.fromPixels((int)e.getX(), (int)e.getY());
                final String longitude = Double.toString(((double)loc.getLongitudeE6())/1000000);
                final String latitude = Double.toString(((double)loc.getLatitudeE6())/1000000);
                System.out.println("- Latitude = " + latitude + ", Longitude = " + longitude );
                ArrayList<OverlayItem> overlayArray = new ArrayList<OverlayItem>();
                OverlayItem mapItem = new OverlayItem("", "", new GeoPoint((((double)loc.getLatitudeE6())/1000000), (((double)loc.getLongitudeE6())/1000000)));
                mapItem.setMarker(marker);
                overlayArray.add(mapItem);
                AlertDialog.Builder dialog = new AlertDialog.Builder(MapActivity.this);
                dialog.setTitle("Confirm Location");
                dialog.setMessage("Do you want to confirm this as project location ?");
                dialog.setPositiveButton("Yes, Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra("Latitude",latitude);
                        intent.putExtra("Longitude",longitude);
                        MapActivity.this.setResult(2,intent);
                        finish();

                    }
                });
                dialog.setNegativeButton("No Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
                if(anotherItemizedIconOverlay==null){
                    anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(getApplicationContext(), overlayArray,null);
                    mapView.getOverlays().add(anotherItemizedIconOverlay);
                    mapView.invalidate();
                }else{
                    mapView.getOverlays().remove(anotherItemizedIconOverlay);
                    mapView.invalidate();
                    anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(getApplicationContext(), overlayArray,null);
                    mapView.getOverlays().add(anotherItemizedIconOverlay);
                }
                //      dlgThread();
                return true;
            }
        };
        map.getOverlays().add(touchOverlay);

    }
    public void onResume(){
        super.onResume();
        map.onResume();
    }

    public void onPause(){
        super.onPause();
        map.onPause();
    }
//    @Override
//    public boolean singleTapConfirmedHelper(GeoPoint p) {
//        mapController.animateTo(p);
////       Drawable newMarker = this.getResources().getDrawable(R.drawable.icon_all_projects);
////        Marker m = new Marker(map);
////        m.setPosition(p);
////        m.setIcon(newMarker);
////        m.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_TOP);
////        map.getOverlays().add(m);
//
//        Toast.makeText(this, String.valueOf(p.getLatitude()), Toast.LENGTH_SHORT).show();
//        return true;
//    }
//
//    @Override
//    public boolean longPressHelper(GeoPoint p) {
//        Toast.makeText(this, "p", Toast.LENGTH_SHORT).show();
//        return false;
//    }
}
