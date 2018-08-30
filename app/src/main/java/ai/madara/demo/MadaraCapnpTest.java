package ai.madara.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import org.capnproto.MessageBuilder;

import ai.madara.demo.capnp.Geo;
import ai.madara.knowledge.Any;
import ai.madara.knowledge.EvalSettings;
import ai.madara.knowledge.KnowledgeBase;
import ai.madara.knowledge.KnowledgeRecord;
import ai.madara.knowledge.Variables;
import ai.madara.transport.QoSTransportSettings;
import ai.madara.transport.TransportContext;
import ai.madara.transport.TransportType;
import ai.madara.transport.filters.LogAggregate;
import ai.madara.transport.filters.Packet;

/**
 * Created by Amit S on 08/07/18.
 *
 * In order to send multicast data from C++ program check out $MADARA_ROOT/tests/test_any_transport_multicast.cpp
 */
public class MadaraCapnpTest extends BaseActivity {

    private static final String TAG = MadaraCapnpTest.class.getSimpleName();

    static {
        System.loadLibrary("MADARA");
    }

    private KnowledgeBase myKnowledgeBase;
    private QoSTransportSettings settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        getSupportActionBar().setTitle("Capnp Test");

        runTest(null);
    }

    public void runTest(View view) {
        runTest();
    }

    @Override
    protected void onPause() {
        super.onPause();

        settings.free();
        myKnowledgeBase.free();
    }

    public void runTest() {
        try {

            registerAny();
            settings = new QoSTransportSettings();
            settings.setHosts(new String[]{"239.255.0.1:4150"});
            settings.setType(TransportType.MULTICAST_TRANSPORT);

            settings.addReceiveFilter(new NetworkFilter());

            // create a myKnowledgeBase base with the multicast transport settings
            myKnowledgeBase = new KnowledgeBase("", settings);
            EvalSettings queueUntilLater = new EvalSettings();
            queueUntilLater.setDelaySendingModifieds(true);

            // set id so we have access to it in the aggregate outgoing filter
            myKnowledgeBase.set(".id", 1);

            addAnyTypes(myKnowledgeBase);

            myKnowledgeBase.sendModifieds();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addAnyTypes(KnowledgeBase knowledge) throws Exception {
        MessageBuilder msg = new MessageBuilder();
        Geo.Point.Builder builder = msg.initRoot(Geo.Point.factory);
        builder.setX(-100);
        builder.setY(32);
        builder.setZ(47);
        Any any = new Any(Geo.Point.factory, msg);
        knowledge.set("point", any);
    }

    private void registerAny() {
        Any.registerClass("Point", Geo.Point.factory);
    }


    private class NetworkFilter extends LogAggregate {

        public void filter(Packet packet, TransportContext context, Variables variables) {
            Log.i(TAG, "Inside Network Filter: " + context.getDomain());
            try {

                myKnowledgeBase.print();

                KnowledgeRecord pointKr = variables.get("point");
                if (pointKr.isValid()) {
                    Any anyPoint = pointKr.toAny();
                    Geo.Point.Reader point = anyPoint.reader(Geo.Point.factory);
                    Log.i(TAG, String.format("Any type Point: (x,y,z) (%f,%f,%f)", point.getX(), point.getY(), point.getZ()));
                }

                KnowledgeRecord network_point = variables.get("pointOverNetwork");
                if (network_point.isValid()) {
                    Any anyPoint = network_point.toAny();
                    Geo.Point.Reader point = anyPoint.reader(Geo.Point.factory);
                    Log.i(TAG, String.format("Any type Point: (x,y,z) (%f,%f,%f)", point.getX(), point.getY(), point.getZ()));
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
