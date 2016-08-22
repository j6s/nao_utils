//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package de.dhbw.wwi13b.shared.util;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Future;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.ALProxy;
import java.util.List;

public class ALLandMarkDetection extends ALProxy {
    private ALLandMarkDetection.AsyncALLandmarkDetection asyncProxy = new ALLandMarkDetection.AsyncALLandmarkDetection();

    public ALLandMarkDetection(Session session) throws Exception {
        super(session);
        System.out.print("LMD has name"+super.getService());
        this.asyncProxy.setService(this.getService());
    }

    public ALLandMarkDetection.AsyncALLandmarkDetection async() {
        return this.asyncProxy;
    }


    public void subscribe(String name, Integer period, Float precision) throws CallError, InterruptedException {
        this.call("subscribe", new Object[]{name, period, precision}).get();
    }

    public void subscribe(String name) throws CallError, InterruptedException {
        this.call("subscribe", new Object[]{name}).get();
    }

    public void unsubscribe(String name) throws CallError, InterruptedException {
        this.call("unsubscribe", new Object[]{name}).get();
    }

    public void updatePeriod(String name, Integer period) throws CallError, InterruptedException {
        this.call("updatePeriod", new Object[]{name, period}).get();
    }

    public void updatePrecision(String name, Float precision) throws CallError, InterruptedException {
        this.call("updatePrecision", new Object[]{name, precision}).get();
    }

    public Integer getCurrentPeriod() throws CallError, InterruptedException {
        return (Integer)this.call("getCurrentPeriod", new Object[0]).get();
    }

    public Float getCurrentPrecision() throws CallError, InterruptedException {
        return (Float)this.call("getCurrentPrecision", new Object[0]).get();
    }

    public Integer getMyPeriod(String name) throws CallError, InterruptedException {
        return (Integer)this.call("getMyPeriod", new Object[]{name}).get();
    }

    public Float getMyPrecision(String name) throws CallError, InterruptedException {
        return (Float)this.call("getMyPrecision", new Object[]{name}).get();
    }


    public List<String> getOutputNames() throws CallError, InterruptedException {
        return (List)this.call("getOutputNames", new Object[0]).get();
    }

    public List<String> getEventList() throws CallError, InterruptedException {
        return (List)this.call("getEventList", new Object[0]).get();
    }

    public List<String> getMemoryKeyList() throws CallError, InterruptedException {
        return (List)this.call("getMemoryKeyList", new Object[0]).get();
    }


    public class AsyncALLandmarkDetection extends ALProxy {
        protected AsyncALLandmarkDetection() {
        }



        public Future<Boolean> setActiveCamera(Integer cameraId) throws CallError, InterruptedException {
            return this.call("setActiveCamera", new Object[]{cameraId});
        }

        public Future<Boolean> isProcessing() throws CallError, InterruptedException {
            return this.call("isProcessing", new Object[0]);
        }

        public Future<Void> pause(Boolean paused) throws CallError, InterruptedException {
            return this.call("pause", new Object[]{paused});
        }



        public Future<List<String>> getMethodList() throws CallError, InterruptedException {
            return this.call("getMethodList", new Object[0]);
        }



        public Future<Boolean> isRunning(Integer id) throws CallError, InterruptedException {
            return this.call("isRunning", new Object[]{id});
        }


        public Future<Void> subscribe(String name, Integer period, Float precision) throws CallError, InterruptedException {
            return this.call("subscribe", new Object[]{name, period, precision});
        }

        public Future<Void> subscribe(String name) throws CallError, InterruptedException {
            return this.call("subscribe", new Object[]{name});
        }

        public Future<Void> unsubscribe(String name) throws CallError, InterruptedException {
            return this.call("unsubscribe", new Object[]{name});
        }

        public Future<Void> updatePeriod(String name, Integer period) throws CallError, InterruptedException {
            return this.call("updatePeriod", new Object[]{name, period});
        }

        public Future<Void> updatePrecision(String name, Float precision) throws CallError, InterruptedException {
            return this.call("updatePrecision", new Object[]{name, precision});
        }

        public Future<Integer> getCurrentPeriod() throws CallError, InterruptedException {
            return this.call("getCurrentPeriod", new Object[0]);
        }

        public Future<Float> getCurrentPrecision() throws CallError, InterruptedException {
            return this.call("getCurrentPrecision", new Object[0]);
        }

        public Future<Integer> getMyPeriod(String name) throws CallError, InterruptedException {
            return this.call("getMyPeriod", new Object[]{name});
        }

        public Future<Float> getMyPrecision(String name) throws CallError, InterruptedException {
            return this.call("getMyPrecision", new Object[]{name});
        }

        public Future<Object> getSubscribersInfo() throws CallError, InterruptedException {
            return this.call("getSubscribersInfo", new Object[0]);
        }

        public Future<List<String>> getOutputNames() throws CallError, InterruptedException {
            return this.call("getOutputNames", new Object[0]);
        }

        public Future<List<String>> getEventList() throws CallError, InterruptedException {
            return this.call("getEventList", new Object[0]);
        }

        public Future<List<String>> getMemoryKeyList() throws CallError, InterruptedException {
            return this.call("getMemoryKeyList", new Object[0]);
        }

    }
}
