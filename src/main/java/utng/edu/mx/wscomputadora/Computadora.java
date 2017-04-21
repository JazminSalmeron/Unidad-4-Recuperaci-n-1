package utng.edu.mx.wscomputadora;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by danie on 21/04/2017.
 */

public class Computadora implements KvmSerializable {

    private int id;
    private int modelo;

    public Computadora(int id, int modelo) {
        this.id = id;
        this.modelo = modelo;
    }

    public Computadora() {

        this(0, 0);
    }

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return id;
            case 1:
                return modelo;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 2;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i) {
            case 0:
                id = Integer.parseInt(o.toString());
                break;
            case 1:
                modelo = Integer.parseInt(o.toString());
                break;
            default:
                break;
        }

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch (i) {
            case 0:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "id";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "modelo";
                break;

            default:
                break;
        }

    }
}
