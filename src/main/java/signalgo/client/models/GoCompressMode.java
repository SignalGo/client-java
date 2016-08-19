package signalgo.client.models;

/**
 * Created by mehdi akbarian on 2016-08-06.
 */
public enum  GoCompressMode {
    None(0),Zip(1);

    private final int id;
    GoCompressMode(int id) {
        this.id=id;
    }
    public int getValue(){
        return id;
    }

    public static GoCompressMode getInstance(int type){
        switch (type){
            case 0:
                return None;
            case 1:
                return Zip;
            default:
                return None;
        }
    }
}
