package utng.edu.mx.wsasentamiento;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by danie on 20/04/2017.
 */

public class Asentamiento implements KvmSerializable {

    private int id;
    private String codigoPostal;
    private int consecutivoCp;
    private int cveEstado;
    private String asentamiento;
    private int activo;
    private String municipio;
    private String tipoAsentamiento;
    private int ciudadAsentamiento;

    public Asentamiento(int id, String codigoPostal, int consecutivoCp, int cveEstado, String asentamiento, int activo,
                        String municipio, String tipoAsentamiento, int ciudadAsentamiento) {
        this.id = id;
        this.codigoPostal = codigoPostal;
        this.consecutivoCp = consecutivoCp;
        this.cveEstado = cveEstado;
        this.asentamiento = asentamiento;
        this.activo = activo;
        this.municipio = municipio;
        this.tipoAsentamiento = tipoAsentamiento;
        this.ciudadAsentamiento = ciudadAsentamiento;
    }

    public Asentamiento() {

        this(0, "", 0, 0, "", 0, "", "", 0);
    }

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return id;
            case 1:
                return codigoPostal;
            case 2:
                return consecutivoCp;
            case 3:
                return cveEstado;
            case 4:
                return asentamiento;
            case 5:
                return activo;
            case 6:
                return municipio;
            case 7:
                return tipoAsentamiento;
            case 8:
                return ciudadAsentamiento;

        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 9;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i) {
            case 0:
                id = Integer.parseInt(o.toString());
                break;
            case 1:
                codigoPostal = o.toString();
                break;
            case 2:
                consecutivoCp = Integer.parseInt(o.toString());
                break;
            case 3:
                cveEstado = Integer.parseInt(o.toString());
                break;
            case 4:
                asentamiento = o.toString();
                break;
            case 5:
                activo = Integer.parseInt(o.toString());
                break;
            case 6:
                municipio = o.toString();
                break;
            case 7:
                tipoAsentamiento = o.toString();
                break;
            case 8:
                ciudadAsentamiento = Integer.parseInt(o.toString());
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
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "codigoPostal";
                break;
            case 2:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "consecutivoCp";
                break;

            case 3:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "cveEstado";
                break;
            case 4:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "asentamiento";
                break;
            case 5:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "activo";
                break;

            case 6:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "municipio";
                break;
            case 7:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "tipoAsentamiento";
                break;
            case 8:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "ciudadAsentamiento";
                break;

            default:
                break;
        }

    }
}