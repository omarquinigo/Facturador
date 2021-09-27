/*
 * Descripción de cada archivo plano
 * Anexos I y II Formato 1.3.4.4
 */
package controlador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import modelo.Baja;
import modelo.Boleta;
import modelo.Factura;
import modelo.NotaCredito;
import modelo.NotaDebito;

public class ArchivosPlanos {

    static ResultSet rs;

    static String porcentajeIgv = "18.00";
    
    // AP (Archivos Planos) según Anexo I SUNAT
    
    // ============== Factura ===============

    public static void apFactura(String id) {
        //codigo tipo comprobante
        String tipoComprobante = Catalogos.tipoDocumento("", "Factura")[0];
        apFacturaCab(id, tipoComprobante, "CAB");
        apFacturaDet(id, tipoComprobante, "DET");
        // tributos;
        boolean[] tributos = tributoFactura(id);
        apFacturaTri(id, tipoComprobante, "TRI", tributos);
        apFacturaLey(id, tipoComprobante, "LEY");
    }

    private static void apFacturaCab(String id, String codigoTipoComprobante, String extension) {
        File ap = new File(Rutas.getRutaAP(codigoTipoComprobante, id, extension));
        BufferedWriter bufferedWriter;
        try {
            rs = Factura.consulta("select * \n"
                    + "from factura \n"
                    + "inner join Cliente \n"
                    + "on Cliente.id = factura.idCliente \n"
                    + "where factura.id = '" + id + "';");
            String fechaEmision = null;
            String horaEmision = null;
            String fechaVencimiento = null;
            String tipoDocumento = null;
            String numeroDocumento = null;
            String nombreRazonSocial = null;
            String moneda = null;
            String igv = null;
            String totalVentasGravadas = null;
            String importeTotal = null;

            while (rs.next()) {
                fechaEmision = rs.getString("fecha");
                horaEmision = rs.getString("horaEmision");
                fechaVencimiento = rs.getString("fechaVencimiento");
                tipoDocumento = rs.getString("tipoDocumento");
                numeroDocumento = rs.getString("numeroDocumento");
                nombreRazonSocial = rs.getString("nombreRazonSocial");
                moneda = rs.getString("moneda");
                igv = rs.getString("igv");
                totalVentasGravadas = rs.getString("totalVentasGravadas");
                importeTotal = rs.getString("importeTotal");

            }
            rs.close();

            bufferedWriter = new BufferedWriter(new FileWriter(ap));
            //18 campos
            String tipOperacion = "0101"; //venta interna
            String fecEmision = Metodos.fechaFormatoSUNAT(fechaEmision);//YYYY-MM-DD
            String horEmision = horaEmision;
            String fecVencimiento = Metodos.fechaFormatoSUNAT(fechaVencimiento);//YYYY-MM-DD
            String codLocalEmisor = "0000";//domicilio fiscal
            String tipDocUsuario = Catalogos.tipoDocumentoIdentidad("", tipoDocumento)[0];// 6 - RUC
            String numDocUsuario = numeroDocumento;
            String rznSocialUsuario = nombreRazonSocial;
            String tipMoneda = moneda;//PEN,USD
            String sumTotTributos = igv;//igv
            String sumTotValVenta = totalVentasGravadas;
            String sumPrecioVenta = importeTotal;
            String sumDescTotal = "0.00";
            String sumOtrosCargos = "0.00";
            String sumTotalAnticipos = "0.00";
            String sumImpVenta = importeTotal;
            String ublVersionId = "2.1";
            String customizationId = "2.0";
            //se esccribe la linea en el archivo
            bufferedWriter.write(
                    tipOperacion + "|"
                    + fecEmision + "|"
                    + horEmision + "|"
                    + fecVencimiento + "|"
                    + codLocalEmisor + "|"
                    + tipDocUsuario + "|"
                    + numDocUsuario + "|"
                    + rznSocialUsuario + "|"
                    + tipMoneda + "|"
                    + sumTotTributos + "|"
                    + sumTotValVenta + "|"
                    + sumPrecioVenta + "|"
                    + sumDescTotal + "|"
                    + sumOtrosCargos + "|"
                    + sumTotalAnticipos + "|"
                    + sumImpVenta + "|"
                    + ublVersionId + "|"
                    + customizationId
            );
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error creando archivo plano " + id + "." + extension + ":\n" + e);
            //Metodos.MensajeError("Error creando archivo plano " + id + "."+extension+":\n" + e);
        }

    }

    private static void apFacturaDet(String id, String codigoTipoComprobante, String extension) {
        File ap = new File(Rutas.getRutaAP(codigoTipoComprobante, id, extension));
        BufferedWriter bufferedWriter;
        try {
            rs = Factura.consulta("select * \n"
                    + "from facturadet \n"
                    + "where idFactura = '" + id + "';");
            bufferedWriter = new BufferedWriter(new FileWriter(ap));
            while (rs.next()) {
                String catidad = rs.getString("cantidad");
                String codigo = rs.getString("codigo");
                String descripcion = rs.getString("descripcion");
                String tributo = rs.getString("tributo");
                String montoTributo = rs.getString("montoTributo");
                String tipoAfectacionTributo = rs.getString("tipoAfectacionTributo");
                String valorUnitario = rs.getString("valorUnitario");
                String valorUnitarioGratuito = rs.getString("valorUnitarioGratuito");
                String precioUnitarioItem = rs.getString("precioUnitarioItem");
                
                //36 campos
                String codUnidadMedida = "EA";
                String ctdUnidadItem = catidad;
                String codProducto = codigo;
                String codProductoSUNAT = "-";
                String desItem = descripcion;
                String mtoValorUnitario = valorUnitario;
                String sumTotTributosItem = String.valueOf(Double.parseDouble(precioUnitarioItem) * 0.18);
                //========== Tributo IGV =============
                //Tributo: IGV(1000) - IVAP(1016) - EXP(9995) - GRA(9996) - EXO(9997) - INA(9998)
                String codTriIGV = Catalogos.tipoTributo("", "", "", tributo)[0];
                String mtoIgvItem = montoTributo;
                String mtoBaseIgvItem = Metodos.formatoDecimalMostrar(
                        (Metodos.formatoDecimalOperar(valorUnitario) + Metodos.formatoDecimalOperar(valorUnitarioGratuito))
                            * Metodos.formatoDecimalOperar(catidad)
                );
                String nomTributoIgvItem = Catalogos.tipoTributo("", "", "", tributo)[3];
                String codTipTributoIgvItem = Catalogos.tipoTributo("", "", "", tributo)[2];
                String tipAfeIGV = tipoAfectacionTributo;
                String porIgvItem = porcentajeIgv;
                //========== Tributo ISC =============
                //Tributo ISC (2000)
                String codTriISC = "-";
                String mtoIscItem = "";
                String mtoBaseIscItem = "";
                String nomTributoIscItem = "";
                String codTipTributoIscItem = "";
                String tipSisISC = "";
                String porIscItem = "";
                //========== Tributo otro ==============
                //Tributo Otro 9999
                String codTriOtro = "-";
                String mtoTriOtroItem = "";
                String mtoBaseTriOtroItem = "";
                String nomTributoOtroItem = "";
                String codTipTributoOtroItem = "";
                String porTriOtroItem = "";
                //========== Tributo ICBPER =============
                //Tributo ICBPER 7152
                String codTriIcbper = "-";
                String mtoTriIcbperItem = "";
                String ctdBolsasTriIcbperItem = "";
                String nomTributoIcbperItem = "";
                String codTipTributoIcbperItem = "";
                String mtoTriIcbperUnidad = "";
                // ======================================
                String mtoPrecioVentaUnitario;
                if (tributo.equals("IGV")) {
                    mtoPrecioVentaUnitario = String.valueOf(
                            ((Double.parseDouble(valorUnitario) + Metodos.formatoDecimalOperar(valorUnitarioGratuito))
                            * (Double.parseDouble(porcentajeIgv) / 100))
                            + (Double.parseDouble(valorUnitario) + Metodos.formatoDecimalOperar(valorUnitarioGratuito))
                    );
                } else { //GRA
                    mtoPrecioVentaUnitario = "0.00";
                }
                String mtoValorVentaItem = Metodos.formatoDecimalMostrar(
                        Metodos.formatoDecimalOperar(catidad)
                        * (Metodos.formatoDecimalOperar(valorUnitario) + Metodos.formatoDecimalOperar(valorUnitarioGratuito))
                );
                String mtoValorReferencialUnitario = valorUnitarioGratuito;

                bufferedWriter.write(
                        codUnidadMedida + "|"
                        + ctdUnidadItem + "|"
                        + codProducto + "|"
                        + codProductoSUNAT + "|"
                        + desItem + "|"
                        + mtoValorUnitario + "|"
                        + sumTotTributosItem + "|"
                        + codTriIGV + "|"
                        + mtoIgvItem + "|"
                        + mtoBaseIgvItem + "|"
                        + nomTributoIgvItem + "|"
                        + codTipTributoIgvItem + "|"
                        + tipAfeIGV + "|"
                        + porIgvItem + "|"
                        + codTriISC + "|"
                        + mtoIscItem + "|"
                        + mtoBaseIscItem + "|"
                        + nomTributoIscItem + "|"
                        + codTipTributoIscItem + "|"
                        + tipSisISC + "|"
                        + porIscItem + "|"
                        + codTriOtro + "|"
                        + mtoTriOtroItem + "|"
                        + mtoBaseTriOtroItem + "|"
                        + nomTributoOtroItem + "|"
                        + codTipTributoOtroItem + "|"
                        + porTriOtroItem + "|"
                        + codTriIcbper + "|"
                        + mtoTriIcbperItem + "|"
                        + ctdBolsasTriIcbperItem + "|"
                        + nomTributoIcbperItem + "|"
                        + codTipTributoIcbperItem + "|"
                        + mtoTriIcbperUnidad + "|"
                        + mtoPrecioVentaUnitario + "|"
                        + mtoValorVentaItem + "|"
                        + mtoValorReferencialUnitario + "|\n");
                
            }
            rs.close();
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error creando archivo plano " + id + "." + extension + ":\n" + e);
            //Metodos.MensajeError("Error creando archivo plano " + id + ".DET:\n" + e);
        }
    }

    private static boolean[] tributoFactura(String id) {
        boolean[] tributos = {false, false, false, false, false, false, false, false, false};

        try {
            rs = Factura.consulta("select * \n"
                    + "from facturadet \n"
                    + "where idFactura = '" + id + "';");
            String tributo;

            while (rs.next()) {
                tributo = rs.getString("tributo");
                switch (tributo) {
                    case "IGV":
                        tributos[0] = true;
                        break;
                    case "IVAP":
                        tributos[1] = true;
                        break;
                    case "ISC":
                        tributos[2] = true;
                        break;
                    case "ICBPER":
                        tributos[3] = true;
                        break;
                    case "EXP":
                        tributos[4] = true;
                        break;
                    case "GRA":
                        tributos[5] = true;
                        break;
                    case "EXO":
                        tributos[6] = true;
                        break;
                    case "INA":
                        tributos[7] = true;
                        break;
                    default: // OTROS
                        tributos[8] = true;
                        break;
                }
            }
            rs.close();
        } catch (Exception e) {

        }
        return tributos;
    }

    private static void apFacturaTri(String id, String codigoTipoComprobante, String extension, boolean tributos[]) {
        File ap = new File(Rutas.getRutaAP(codigoTipoComprobante, id, extension));
        BufferedWriter bufferedWriter;
        try {
            rs = Factura.consulta("select * \n"
                    + "from factura \n"
                    + "where id = '" + id + "';");
            String igv = "0.00";
            String totalVentasGravadas = "0.00";
            String totalGratuito = "0.00";
            while (rs.next()) {
                igv = rs.getString("igv");
                totalVentasGravadas = rs.getString("totalVentasGravadas");
                totalGratuito = rs.getString("totalGratuito");
            }
            rs.close();
            
            bufferedWriter = new BufferedWriter(new FileWriter(ap));
            if (tributos[0] == true) { // IGV
                
                //5 campos
                String ideTributo = Catalogos.tipoTributo("1000", "", "", "")[0];
                String nomTributo = Catalogos.tipoTributo("1000", "", "", "")[3];
                String codTipTributo = Catalogos.tipoTributo("1000", "", "", "")[2];
                String mtoBaseImponible = totalVentasGravadas;
                String mtoTributo = igv;
                //se esccribe la linea en el archivo
                bufferedWriter.write(
                        ideTributo + "|"
                        + nomTributo + "|"
                        + codTipTributo + "|"
                        + mtoBaseImponible + "|"
                        + mtoTributo + "|\n");
                //bufferedWriter.close();
            }
            if (tributos[5] == true) { // GRA
                //bufferedWriter = new BufferedWriter(new FileWriter(ap));
                //5 campos
                String ideTributo = Catalogos.tipoTributo("9996", "", "", "")[0];
                String nomTributo = Catalogos.tipoTributo("9996", "", "", "")[3];
                String codTipTributo = Catalogos.tipoTributo("9996", "", "", "")[2];
                String mtoBaseImponible = totalGratuito;
                String mtoTributo = "0.00";
                //se esccribe la linea en el archivo
                bufferedWriter.write(
                        ideTributo + "|"
                        + nomTributo + "|"
                        + codTipTributo + "|"
                        + mtoBaseImponible + "|"
                        + mtoTributo + "|\n");
                bufferedWriter.close();
            }
            if (tributos[7] == true) {//INA 

            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error creando archivo plano " + id + "." + extension + ":\n" + e);
            //Metodos.MensajeError("Error creando archivo plano " + id + ".TRI:\n" + e);
        }

    }

    private static void apFacturaLey(String id, String codigoTipoComprobante, String extension) {
        File ap = new File(Rutas.getRutaAP(codigoTipoComprobante, id, extension));
        BufferedWriter bufferedWriter;
        try {
            rs = Factura.consulta("select * \n"
                    + "from factura \n"
                    + "where id = '" + id + "';");

            String moneda = null;
            double totalGratuito = 0.00;
            double importeTotal = 0.00;

            while (rs.next()) {
                moneda = rs.getString("moneda");
                totalGratuito = Double.parseDouble(rs.getString("totalGratuito"));
                importeTotal = Double.parseDouble(rs.getString("importeTotal"));
            }
            rs.close();
            bufferedWriter = new BufferedWriter(new FileWriter(ap));
            //2 campos
            String codLeyenda = Catalogos.tipoLeyenda("1000", "")[0];
            String desLeyenda = Metodos.convertirNumTexto(Metodos.formatoDecimalMostrar(importeTotal), moneda);
            //se escribe la linea en el archivo
            bufferedWriter.write(
                    codLeyenda + "|"
                    + desLeyenda + "|\n"
            );

            if (totalGratuito > 0.00 || importeTotal == 0.00) {
                // 1002 Leyenda "TRANSFERENCIA GRATUITA DE UN BIEN Y/O SERVICIO PRESTADO GRATUITAMENTE"
                //2 campos
                String codLeyendaGratuita = Catalogos.tipoLeyenda("1002", "")[0];
                String desLeyendaGratuita = Catalogos.tipoLeyenda("1002", "")[1];
                //se escribe la linea en el archivo
                bufferedWriter.write(
                        codLeyendaGratuita + "|"
                        + desLeyendaGratuita + "|\n"
                );
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error creando archivo plano " + id + "." + extension + ":\n" + e);
            //Metodos.MensajeError("Error creando archivo plano " + id + ".LEY:\n" + e);
        }

    }
    
    // ============== Boleta ===============
    
    public static void apBoleta(String id) {
        //codigo tipo comprobante
        String tipoComprobante = Catalogos.tipoDocumento("", "Boleta de venta")[0];
        apBoletaCab(id, tipoComprobante, "CAB");
        apBoletaDet(id, tipoComprobante, "DET");
        // tributos;
        boolean[] tributos = tributoBoleta(id);
        apBoletaTri(id, tipoComprobante, "TRI", tributos);
        apBoletaLey(id, tipoComprobante, "LEY");
    }
    
    private static void apBoletaCab(String id, String codigoTipoComprobante, String extension) {
        File ap = new File(Rutas.getRutaAP(codigoTipoComprobante, id, extension));
        BufferedWriter bufferedWriter;
        try {
            rs = Boleta.consulta("select * \n"
                    + "from boleta \n"
                    + "inner join cliente \n"
                    + "on cliente.id = boleta.idCliente \n"
                    + "where boleta.id = '" + id + "';");
            String fechaEmision = null;
            String horaEmision = null;
            String fechaVencimiento = null;
            String tipoDocumento = null;
            String numeroDocumento = null;
            String nombreRazonSocial = null;
            String moneda = null;
            String igv = null;
            String totalVentasGravadas = null;
            String importeTotal = null;

            while (rs.next()) {
                fechaEmision = rs.getString("fecha");
                horaEmision = rs.getString("horaEmision");
                fechaVencimiento = rs.getString("fechaVencimiento");
                tipoDocumento = rs.getString("tipoDocumento");
                numeroDocumento = rs.getString("numeroDocumento");
                nombreRazonSocial = rs.getString("nombreRazonSocial");
                moneda = rs.getString("moneda");
                igv = rs.getString("igv");
                totalVentasGravadas = rs.getString("totalVentasGravadas");
                importeTotal = rs.getString("importeTotal");

            }
            rs.close();

            bufferedWriter = new BufferedWriter(new FileWriter(ap));
            //18 campos
            String tipOperacion = "0101"; //venta interna
            String fecEmision = Metodos.fechaFormatoSUNAT(fechaEmision);//YYYY-MM-DD
            String horEmision = horaEmision;
            String fecVencimiento = Metodos.fechaFormatoSUNAT(fechaVencimiento);//YYYY-MM-DD
            String codLocalEmisor = "0000";//domicilio fiscal
            String tipDocUsuario = Catalogos.tipoDocumentoIdentidad("", tipoDocumento)[0];// DNI/canet ex/pass
            String numDocUsuario = numeroDocumento;
            String rznSocialUsuario = nombreRazonSocial;
            String tipMoneda = moneda;//PEN,USD
            String sumTotTributos = igv;//igv
            String sumTotValVenta = totalVentasGravadas;
            String sumPrecioVenta = importeTotal;
            String sumDescTotal = "0.00";
            String sumOtrosCargos = "0.00";
            String sumTotalAnticipos = "0.00";
            String sumImpVenta = importeTotal;
            String ublVersionId = "2.1";
            String customizationId = "2.0";
            //se esccribe la linea en el archivo
            bufferedWriter.write(
                    tipOperacion + "|"
                    + fecEmision + "|"
                    + horEmision + "|"
                    + fecVencimiento + "|"
                    + codLocalEmisor + "|"
                    + tipDocUsuario + "|"
                    + numDocUsuario + "|"
                    + rznSocialUsuario + "|"
                    + tipMoneda + "|"
                    + sumTotTributos + "|"
                    + sumTotValVenta + "|"
                    + sumPrecioVenta + "|"
                    + sumDescTotal + "|"
                    + sumOtrosCargos + "|"
                    + sumTotalAnticipos + "|"
                    + sumImpVenta + "|"
                    + ublVersionId + "|"
                    + customizationId
            );
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error creando archivo plano " + id + "." + extension + ":\n" + e);
            //Metodos.MensajeError("Error creando archivo plano " + id + "."+extension+":\n" + e);
        }

    }

    private static void apBoletaDet(String id, String codigoTipoComprobante, String extension) {
        File ap = new File(Rutas.getRutaAP(codigoTipoComprobante, id, extension));
        BufferedWriter bufferedWriter;
        try {
            rs = Boleta.consulta("select * \n"
                    + "from boletadet \n"
                    + "where idBoleta = '" + id + "';");
            bufferedWriter = new BufferedWriter(new FileWriter(ap));
            while (rs.next()) {
                String catidad = rs.getString("cantidad");
                String codigo = rs.getString("codigo");
                String descripcion = rs.getString("descripcion");
                String tributo = rs.getString("tributo");
                String montoTributo = rs.getString("montoTributo");
                String tipoAfectacionTributo = rs.getString("tipoAfectacionTributo");
                String valorUnitario = rs.getString("valorUnitario");
                String valorUnitarioGratuito = rs.getString("valorUnitarioGratuito");
                String precioUnitarioItem = rs.getString("precioUnitarioItem");
                
                //36 campos
                String codUnidadMedida = "EA";
                String ctdUnidadItem = catidad;
                String codProducto = codigo;
                String codProductoSUNAT = "-";
                String desItem = descripcion;
                String mtoValorUnitario = valorUnitario;
                String sumTotTributosItem = String.valueOf(Double.parseDouble(precioUnitarioItem) * 0.18);
                //========== Tributo IGV =============
                //Tributo: IGV(1000) - IVAP(1016) - EXP(9995) - GRA(9996) - EXO(9997) - INA(9998)
                String codTriIGV = Catalogos.tipoTributo("", "", "", tributo)[0];
                String mtoIgvItem = montoTributo;
                String mtoBaseIgvItem = Metodos.formatoDecimalMostrar(
                        (Metodos.formatoDecimalOperar(valorUnitario) + Metodos.formatoDecimalOperar(valorUnitarioGratuito))
                            * Metodos.formatoDecimalOperar(catidad)
                );
                String nomTributoIgvItem = Catalogos.tipoTributo("", "", "", tributo)[3];
                String codTipTributoIgvItem = Catalogos.tipoTributo("", "", "", tributo)[2];
                String tipAfeIGV = tipoAfectacionTributo;
                String porIgvItem = porcentajeIgv;
                //========== Tributo ISC =============
                //Tributo ISC (2000)
                String codTriISC = "-";
                String mtoIscItem = "";
                String mtoBaseIscItem = "";
                String nomTributoIscItem = "";
                String codTipTributoIscItem = "";
                String tipSisISC = "";
                String porIscItem = "";
                //========== Tributo otro ==============
                //Tributo Otro 9999
                String codTriOtro = "-";
                String mtoTriOtroItem = "";
                String mtoBaseTriOtroItem = "";
                String nomTributoOtroItem = "";
                String codTipTributoOtroItem = "";
                String porTriOtroItem = "";
                //========== Tributo ICBPER =============
                //Tributo ICBPER 7152
                String codTriIcbper = "-";
                String mtoTriIcbperItem = "";
                String ctdBolsasTriIcbperItem = "";
                String nomTributoIcbperItem = "";
                String codTipTributoIcbperItem = "";
                String mtoTriIcbperUnidad = "";
                // ======================================
                String mtoPrecioVentaUnitario;
                if (tributo.equals("IGV")) {
                    mtoPrecioVentaUnitario = String.valueOf(
                            ((Double.parseDouble(valorUnitario) + Metodos.formatoDecimalOperar(valorUnitarioGratuito))
                            * (Double.parseDouble(porcentajeIgv) / 100))
                            + (Double.parseDouble(valorUnitario) + Metodos.formatoDecimalOperar(valorUnitarioGratuito))
                    );
                } else { //GRA
                    mtoPrecioVentaUnitario = "0.00";
                }
                String mtoValorVentaItem = Metodos.formatoDecimalMostrar(
                        Metodos.formatoDecimalOperar(catidad)
                        * (Metodos.formatoDecimalOperar(valorUnitario) + Metodos.formatoDecimalOperar(valorUnitarioGratuito))
                );
                String mtoValorReferencialUnitario = valorUnitarioGratuito;

                bufferedWriter.write(
                        codUnidadMedida + "|"
                        + ctdUnidadItem + "|"
                        + codProducto + "|"
                        + codProductoSUNAT + "|"
                        + desItem + "|"
                        + mtoValorUnitario + "|"
                        + sumTotTributosItem + "|"
                        + codTriIGV + "|"
                        + mtoIgvItem + "|"
                        + mtoBaseIgvItem + "|"
                        + nomTributoIgvItem + "|"
                        + codTipTributoIgvItem + "|"
                        + tipAfeIGV + "|"
                        + porIgvItem + "|"
                        + codTriISC + "|"
                        + mtoIscItem + "|"
                        + mtoBaseIscItem + "|"
                        + nomTributoIscItem + "|"
                        + codTipTributoIscItem + "|"
                        + tipSisISC + "|"
                        + porIscItem + "|"
                        + codTriOtro + "|"
                        + mtoTriOtroItem + "|"
                        + mtoBaseTriOtroItem + "|"
                        + nomTributoOtroItem + "|"
                        + codTipTributoOtroItem + "|"
                        + porTriOtroItem + "|"
                        + codTriIcbper + "|"
                        + mtoTriIcbperItem + "|"
                        + ctdBolsasTriIcbperItem + "|"
                        + nomTributoIcbperItem + "|"
                        + codTipTributoIcbperItem + "|"
                        + mtoTriIcbperUnidad + "|"
                        + mtoPrecioVentaUnitario + "|"
                        + mtoValorVentaItem + "|"
                        + mtoValorReferencialUnitario + "|\n");
                
            }
            rs.close();
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error creando archivo plano " + id + "." + extension + ":\n" + e);
            //Metodos.MensajeError("Error creando archivo plano " + id + ".DET:\n" + e);
        }
    }
    
    private static boolean[] tributoBoleta(String id) {
        boolean[] tributos = {false, false, false, false, false, false, false, false, false};

        try {
            rs = Boleta.consulta("select * \n"
                    + "from boletadet \n"
                    + "where idBoleta = '" + id + "';");
            String tributo;

            while (rs.next()) {
                tributo = rs.getString("tributo");
                switch (tributo) {
                    case "IGV":
                        tributos[0] = true;
                        break;
                    case "IVAP":
                        tributos[1] = true;
                        break;
                    case "ISC":
                        tributos[2] = true;
                        break;
                    case "ICBPER":
                        tributos[3] = true;
                        break;
                    case "EXP":
                        tributos[4] = true;
                        break;
                    case "GRA":
                        tributos[5] = true;
                        break;
                    case "EXO":
                        tributos[6] = true;
                        break;
                    case "INA":
                        tributos[7] = true;
                        break;
                    default: // OTROS
                        tributos[8] = true;
                        break;
                }
            }
            rs.close();
        } catch (Exception e) {

        }
        return tributos;
    }
    
    private static void apBoletaTri(String id, String codigoTipoComprobante, String extension, boolean tributos[]) {
        File ap = new File(Rutas.getRutaAP(codigoTipoComprobante, id, extension));
        BufferedWriter bufferedWriter;
        try {
            rs = Boleta.consulta("select * \n"
                    + "from boleta \n"
                    + "where id = '" + id + "';");
            String igv = "0.00";
            String totalVentasGravadas = "0.00";
            String totalGratuito = "0.00";
            while (rs.next()) {
                igv = rs.getString("igv");
                totalVentasGravadas = rs.getString("totalVentasGravadas");
                totalGratuito = rs.getString("totalGratuito");
            }
            rs.close();
            
            bufferedWriter = new BufferedWriter(new FileWriter(ap));
            if (tributos[0] == true) { // IGV
                
                //5 campos
                String ideTributo = Catalogos.tipoTributo("1000", "", "", "")[0];
                String nomTributo = Catalogos.tipoTributo("1000", "", "", "")[3];
                String codTipTributo = Catalogos.tipoTributo("1000", "", "", "")[2];
                String mtoBaseImponible = totalVentasGravadas;
                String mtoTributo = igv;
                //se esccribe la linea en el archivo
                bufferedWriter.write(
                        ideTributo + "|"
                        + nomTributo + "|"
                        + codTipTributo + "|"
                        + mtoBaseImponible + "|"
                        + mtoTributo + "|\n");
                //bufferedWriter.close();
            }
            if (tributos[5] == true) { // GRA
                //bufferedWriter = new BufferedWriter(new FileWriter(ap));
                //5 campos
                String ideTributo = Catalogos.tipoTributo("9996", "", "", "")[0];
                String nomTributo = Catalogos.tipoTributo("9996", "", "", "")[3];
                String codTipTributo = Catalogos.tipoTributo("9996", "", "", "")[2];
                String mtoBaseImponible = totalGratuito;
                String mtoTributo = "0.00";
                //se esccribe la linea en el archivo
                bufferedWriter.write(
                        ideTributo + "|"
                        + nomTributo + "|"
                        + codTipTributo + "|"
                        + mtoBaseImponible + "|"
                        + mtoTributo + "|\n");
                bufferedWriter.close();
            }
            if (tributos[7] == true) {//INA 

            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error creando archivo plano " + id + "." + extension + ":\n" + e);
            //Metodos.MensajeError("Error creando archivo plano " + id + ".TRI:\n" + e);
        }

    }

    private static void apBoletaLey(String id, String codigoTipoComprobante, String extension) {
        File ap = new File(Rutas.getRutaAP(codigoTipoComprobante, id, extension));
        BufferedWriter bufferedWriter;
        try {
            rs = Boleta.consulta("select * \n"
                    + "from boleta \n"
                    + "where id = '" + id + "';");

            String moneda = null;
            double totalGratuito = 0.00;
            double importeTotal = 0.00;

            while (rs.next()) {
                moneda = rs.getString("moneda");
                totalGratuito = Double.parseDouble(rs.getString("totalGratuito"));
                importeTotal = Double.parseDouble(rs.getString("importeTotal"));
            }
            rs.close();
            bufferedWriter = new BufferedWriter(new FileWriter(ap));
            //2 campos
            String codLeyenda = Catalogos.tipoLeyenda("1000", "")[0];
            String desLeyenda = Metodos.convertirNumTexto(Metodos.formatoDecimalMostrar(importeTotal), moneda);
            //se escribe la linea en el archivo
            bufferedWriter.write(
                    codLeyenda + "|"
                    + desLeyenda + "|\n"
            );

            if (totalGratuito > 0.00 || importeTotal == 0.00) {
                // 1002 Leyenda "TRANSFERENCIA GRATUITA DE UN BIEN Y/O SERVICIO PRESTADO GRATUITAMENTE"
                //2 campos
                String codLeyendaGratuita = Catalogos.tipoLeyenda("1002", "")[0];
                String desLeyendaGratuita = Catalogos.tipoLeyenda("1002", "")[1];
                //se escribe la linea en el archivo
                bufferedWriter.write(
                        codLeyendaGratuita + "|"
                        + desLeyendaGratuita + "|\n"
                );
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error creando archivo plano " + id + "." + extension + ":\n" + e);
            //Metodos.MensajeError("Error creando archivo plano " + id + ".LEY:\n" + e);
        }

    }
    
    // ============== Nota Crédito ===============
    
    public static void apNotaCredito(String id) {
        //codigo tipo comprobante
        String tipoComprobante = Catalogos.tipoDocumento("", "Nota de crédito")[0];
        apNotaCreditoNot(id, tipoComprobante, "NOT");
        apNotaCreditoDet(id, tipoComprobante, "DET");
        // tributos;
        boolean[] tributos = tributoNotaCredito(id);
        apNotaCreditoTri(id, tipoComprobante, "TRI", tributos);
        apNotaCreditoLey(id, tipoComprobante, "LEY");
    }
    
    private static void apNotaCreditoNot(String id, String codigoTipoComprobante, String extension) {
        File ap = new File(Rutas.getRutaAP(codigoTipoComprobante, id, extension));
        BufferedWriter bufferedWriter;
        try {
            String cpe,codigoTipoComprobanteElectronico;
            if(id.contains("FC")){
                cpe = "factura";
                codigoTipoComprobanteElectronico = "01";
            } else {
                cpe = "boleta";
                codigoTipoComprobanteElectronico = "03";
            }
            rs = NotaCredito.consulta("select * \n"
                    + "from notacredito \n"
                    + "inner join "+cpe+" \n"
                    + "on "+cpe+".id = notacredito.idComprobante \n"
                    + "inner join cliente \n"
                    + "on cliente.id = "+cpe+".idCliente \n"
                    + "where notacredito.id = '" + id + "';");
            String idComprobante = null;
            String fechaEmision = null;
            String horaEmision = null;
            String tipoDocumento = null;
            String numeroDocumento = null;
            String nombreRazonSocial = null;
            String moneda = null;
            String motivo = null;
            String igv = null;
            String totalVentasGravadas = null;
            String importeTotal = null;

            while (rs.next()) {
                idComprobante = rs.getString("idComprobante");
                fechaEmision = rs.getString("fecha");
                horaEmision = rs.getString("horaEmision");
                tipoDocumento = rs.getString("tipoDocumento");
                numeroDocumento = rs.getString("numeroDocumento");
                nombreRazonSocial = rs.getString("nombreRazonSocial");
                moneda = rs.getString("moneda");
                motivo = rs.getString("motivo");
                igv = rs.getString("igv");
                totalVentasGravadas = rs.getString("totalVentasGravadas");
                importeTotal = rs.getString("importeTotal");
            }
            rs.close();

            bufferedWriter = new BufferedWriter(new FileWriter(ap));
            //21 campos
            String tipOperacion = "0101"; //venta interna
            String fecEmision = Metodos.fechaFormatoSUNAT(fechaEmision);//YYYY-MM-DD
            String horEmision = horaEmision;
            String codLocalEmisor = "0000";//domicilio fiscal
            String tipDocUsuario = Catalogos.tipoDocumentoIdentidad("", tipoDocumento)[0];// DNI/canet ex/pass
            String numDocUsuario = numeroDocumento;
            String rznSocialUsuario = nombreRazonSocial;
            String tipMoneda = moneda;//PEN,USD
            String codMotivo = Catalogos.tipoNotaCredito("", motivo)[0];
            String desMotivo = motivo;
            String tipDocAfectado = codigoTipoComprobanteElectronico;
            String numDocAfectado = idComprobante;
            String sumTotTributos = igv;//igv
            String sumTotValVenta = totalVentasGravadas;
            String sumPrecioVenta = importeTotal;
            String sumDescTotal = "0.00";
            String sumOtrosCargos = "0.00";
            String sumTotalAnticipos = "0.00";
            String sumImpVenta = importeTotal;
            String ublVersionId = "2.1";
            String customizationId = "2.0";
            //se esccribe la linea en el archivo
            bufferedWriter.write(
                    tipOperacion + "|"
                    + fecEmision + "|"
                    + horEmision + "|"
                    + codLocalEmisor + "|"
                    + tipDocUsuario + "|"
                    + numDocUsuario + "|"
                    + rznSocialUsuario + "|"
                    + tipMoneda + "|"
                    + codMotivo + "|"
                    + desMotivo + "|"
                    + tipDocAfectado + "|"
                    + numDocAfectado + "|"
                    + sumTotTributos + "|"
                    + sumTotValVenta + "|"
                    + sumPrecioVenta + "|"
                    + sumDescTotal + "|"
                    + sumOtrosCargos + "|"
                    + sumTotalAnticipos + "|"
                    + sumImpVenta + "|"
                    + ublVersionId + "|"
                    + customizationId
            );
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error creando archivo plano " + id + "." + extension + ":\n" + e);
            //Metodos.MensajeError("Error creando archivo plano " + id + "."+extension+":\n" + e);
        }

    }
    
    private static void apNotaCreditoDet(String id, String codigoTipoComprobante, String extension) {
        File ap = new File(Rutas.getRutaAP(codigoTipoComprobante, id, extension));
        BufferedWriter bufferedWriter;
        try {
            rs = Boleta.consulta("select * \n"
                    + "from notacreditodet \n"
                    + "where idNotaCredito = '" + id + "';");
            bufferedWriter = new BufferedWriter(new FileWriter(ap));
            while (rs.next()) {
                String catidad = rs.getString("cantidad");
                String codigo = rs.getString("codigo");
                String descripcion = rs.getString("descripcion");
                String tributo = rs.getString("tributo");
                String montoTributo = rs.getString("montoTributo");
                String tipoAfectacionTributo = rs.getString("tipoAfectacionTributo");
                String valorUnitario = rs.getString("valorUnitario");
                String valorUnitarioGratuito = rs.getString("valorUnitarioGratuito");
                String precioUnitarioItem = rs.getString("precioUnitarioItem");
                
                //36 campos
                String codUnidadMedida = "EA";
                String ctdUnidadItem = catidad;
                String codProducto = codigo;
                String codProductoSUNAT = "-";
                String desItem = descripcion;
                String mtoValorUnitario = valorUnitario;
                String sumTotTributosItem = String.valueOf(Double.parseDouble(precioUnitarioItem) * 0.18);
                //========== Tributo IGV =============
                //Tributo: IGV(1000) - IVAP(1016) - EXP(9995) - GRA(9996) - EXO(9997) - INA(9998)
                String codTriIGV = Catalogos.tipoTributo("", "", "", tributo)[0];
                String mtoIgvItem = montoTributo;
                String mtoBaseIgvItem = Metodos.formatoDecimalMostrar(
                        (Metodos.formatoDecimalOperar(valorUnitario) + Metodos.formatoDecimalOperar(valorUnitarioGratuito))
                            * Metodos.formatoDecimalOperar(catidad)
                );
                String nomTributoIgvItem = Catalogos.tipoTributo("", "", "", tributo)[3];
                String codTipTributoIgvItem = Catalogos.tipoTributo("", "", "", tributo)[2];
                String tipAfeIGV = tipoAfectacionTributo;
                String porIgvItem = porcentajeIgv;
                //========== Tributo ISC =============
                //Tributo ISC (2000)
                String codTriISC = "-";
                String mtoIscItem = "";
                String mtoBaseIscItem = "";
                String nomTributoIscItem = "";
                String codTipTributoIscItem = "";
                String tipSisISC = "";
                String porIscItem = "";
                //========== Tributo otro ==============
                //Tributo Otro 9999
                String codTriOtro = "-";
                String mtoTriOtroItem = "";
                String mtoBaseTriOtroItem = "";
                String nomTributoOtroItem = "";
                String codTipTributoOtroItem = "";
                String porTriOtroItem = "";
                //========== Tributo ICBPER =============
                //Tributo ICBPER 7152
                String codTriIcbper = "-";
                String mtoTriIcbperItem = "";
                String ctdBolsasTriIcbperItem = "";
                String nomTributoIcbperItem = "";
                String codTipTributoIcbperItem = "";
                String mtoTriIcbperUnidad = "";
                // ======================================
                String mtoPrecioVentaUnitario;
                if (tributo.equals("IGV")) {
                    mtoPrecioVentaUnitario = String.valueOf(
                            ((Double.parseDouble(valorUnitario) + Metodos.formatoDecimalOperar(valorUnitarioGratuito))
                            * (Double.parseDouble(porcentajeIgv) / 100))
                            + (Double.parseDouble(valorUnitario) + Metodos.formatoDecimalOperar(valorUnitarioGratuito))
                    );
                } else { //GRA
                    mtoPrecioVentaUnitario = "0.00";
                }
                String mtoValorVentaItem = Metodos.formatoDecimalMostrar(
                        Metodos.formatoDecimalOperar(catidad)
                        * (Metodos.formatoDecimalOperar(valorUnitario) + Metodos.formatoDecimalOperar(valorUnitarioGratuito))
                );
                String mtoValorReferencialUnitario = valorUnitarioGratuito;

                bufferedWriter.write(
                        codUnidadMedida + "|"
                        + ctdUnidadItem + "|"
                        + codProducto + "|"
                        + codProductoSUNAT + "|"
                        + desItem + "|"
                        + mtoValorUnitario + "|"
                        + sumTotTributosItem + "|"
                        + codTriIGV + "|"
                        + mtoIgvItem + "|"
                        + mtoBaseIgvItem + "|"
                        + nomTributoIgvItem + "|"
                        + codTipTributoIgvItem + "|"
                        + tipAfeIGV + "|"
                        + porIgvItem + "|"
                        + codTriISC + "|"
                        + mtoIscItem + "|"
                        + mtoBaseIscItem + "|"
                        + nomTributoIscItem + "|"
                        + codTipTributoIscItem + "|"
                        + tipSisISC + "|"
                        + porIscItem + "|"
                        + codTriOtro + "|"
                        + mtoTriOtroItem + "|"
                        + mtoBaseTriOtroItem + "|"
                        + nomTributoOtroItem + "|"
                        + codTipTributoOtroItem + "|"
                        + porTriOtroItem + "|"
                        + codTriIcbper + "|"
                        + mtoTriIcbperItem + "|"
                        + ctdBolsasTriIcbperItem + "|"
                        + nomTributoIcbperItem + "|"
                        + codTipTributoIcbperItem + "|"
                        + mtoTriIcbperUnidad + "|"
                        + mtoPrecioVentaUnitario + "|"
                        + mtoValorVentaItem + "|"
                        + mtoValorReferencialUnitario + "|\n");
            }
            rs.close();
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error creando archivo plano " + id + "." + extension + ":\n" + e);
            //Metodos.MensajeError("Error creando archivo plano " + id + ".DET:\n" + e);
        }
    }
    
    private static boolean[] tributoNotaCredito(String id) {
        boolean[] tributos = {false, false, false, false, false, false, false, false, false};

        try {
            rs = NotaCredito.consulta("select * \n"
                    + "from notacreditodet \n"
                    + "where idNotaCredito = '" + id + "';");
            String tributo;

            while (rs.next()) {
                tributo = rs.getString("tributo");
                switch (tributo) {
                    case "IGV":
                        tributos[0] = true;
                        break;
                    case "IVAP":
                        tributos[1] = true;
                        break;
                    case "ISC":
                        tributos[2] = true;
                        break;
                    case "ICBPER":
                        tributos[3] = true;
                        break;
                    case "EXP":
                        tributos[4] = true;
                        break;
                    case "GRA":
                        tributos[5] = true;
                        break;
                    case "EXO":
                        tributos[6] = true;
                        break;
                    case "INA":
                        tributos[7] = true;
                        break;
                    default: // OTROS
                        tributos[8] = true;
                        break;
                }
            }
            rs.close();
        } catch (Exception e) {

        }
        return tributos;
    }
    
    private static void apNotaCreditoTri(String id, String codigoTipoComprobante, String extension, boolean tributos[]) {
        File ap = new File(Rutas.getRutaAP(codigoTipoComprobante, id, extension));
        BufferedWriter bufferedWriter;
        try {
            rs = Boleta.consulta("select * \n"
                    + "from notacredito \n"
                    + "where id = '" + id + "';");
            String igv = "0.00";
            String totalVentasGravadas = "0.00";
            String totalGratuito = "0.00";
            while (rs.next()) {
                igv = rs.getString("igv");
                totalVentasGravadas = rs.getString("totalVentasGravadas");
                totalGratuito = rs.getString("totalGratuito");
            }
            rs.close();
            
            bufferedWriter = new BufferedWriter(new FileWriter(ap));
            if (tributos[0] == true) { // IGV
                
                //5 campos
                String ideTributo = Catalogos.tipoTributo("1000", "", "", "")[0];
                String nomTributo = Catalogos.tipoTributo("1000", "", "", "")[3];
                String codTipTributo = Catalogos.tipoTributo("1000", "", "", "")[2];
                String mtoBaseImponible = totalVentasGravadas;
                String mtoTributo = igv;
                //se esccribe la linea en el archivo
                bufferedWriter.write(
                        ideTributo + "|"
                        + nomTributo + "|"
                        + codTipTributo + "|"
                        + mtoBaseImponible + "|"
                        + mtoTributo + "|\n");
                //bufferedWriter.close();
            }
            if (tributos[5] == true) { // GRA
                //bufferedWriter = new BufferedWriter(new FileWriter(ap));
                //5 campos
                String ideTributo = Catalogos.tipoTributo("9996", "", "", "")[0];
                String nomTributo = Catalogos.tipoTributo("9996", "", "", "")[3];
                String codTipTributo = Catalogos.tipoTributo("9996", "", "", "")[2];
                String mtoBaseImponible = totalGratuito;
                String mtoTributo = "0.00";
                //se esccribe la linea en el archivo
                bufferedWriter.write(
                        ideTributo + "|"
                        + nomTributo + "|"
                        + codTipTributo + "|"
                        + mtoBaseImponible + "|"
                        + mtoTributo + "|\n");
                bufferedWriter.close();
            }
            if (tributos[7] == true) {//INA 

            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error creando archivo plano " + id + "." + extension + ":\n" + e);
            //Metodos.MensajeError("Error creando archivo plano " + id + ".TRI:\n" + e);
        }

    }

    private static void apNotaCreditoLey(String id, String codigoTipoComprobante, String extension) {
        File ap = new File(Rutas.getRutaAP(codigoTipoComprobante, id, extension));
        BufferedWriter bufferedWriter;
        try {
            rs = Boleta.consulta("select * \n"
                    + "from notacredito \n"
                    + "where id = '" + id + "';");

            String moneda = null;
            double totalGratuito = 0.00;
            double importeTotal = 0.00;

            while (rs.next()) {
                moneda = rs.getString("moneda");
                totalGratuito = Double.parseDouble(rs.getString("totalGratuito"));
                importeTotal = Double.parseDouble(rs.getString("importeTotal"));
            }
            rs.close();
            bufferedWriter = new BufferedWriter(new FileWriter(ap));
            //2 campos
            String codLeyenda = Catalogos.tipoLeyenda("1000", "")[0];
            String desLeyenda = Metodos.convertirNumTexto(Metodos.formatoDecimalMostrar(importeTotal), moneda);
            //se escribe la linea en el archivo
            bufferedWriter.write(
                    codLeyenda + "|"
                    + desLeyenda + "|\n"
            );

            if (totalGratuito > 0.00 || importeTotal == 0.00) {
                // 1002 Leyenda "TRANSFERENCIA GRATUITA DE UN BIEN Y/O SERVICIO PRESTADO GRATUITAMENTE"
                //2 campos
                String codLeyendaGratuita = Catalogos.tipoLeyenda("1002", "")[0];
                String desLeyendaGratuita = Catalogos.tipoLeyenda("1002", "")[1];
                //se escribe la linea en el archivo
                bufferedWriter.write(
                        codLeyendaGratuita + "|"
                        + desLeyendaGratuita + "|\n"
                );
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error creando archivo plano " + id + "." + extension + ":\n" + e);
            //Metodos.MensajeError("Error creando archivo plano " + id + ".LEY:\n" + e);
        }

    }
    
    // ============== Nota Débito ===============
    
    public static void apNotaDebito(String id) {
        //codigo tipo comprobante
        String tipoComprobante = Catalogos.tipoDocumento("", "Nota de débito")[0];
        apNotaDebitoNot(id, tipoComprobante, "NOT");
        apNotaDebitoDet(id, tipoComprobante, "DET");
        // tributos;
        boolean[] tributos = tributoNotaDebito(id);
        apNotaDebitoTri(id, tipoComprobante, "TRI", tributos);
        apNotaDebitoLey(id, tipoComprobante, "LEY");
    }
    
    private static void apNotaDebitoNot(String id, String codigoTipoComprobante, String extension) {
        File ap = new File(Rutas.getRutaAP(codigoTipoComprobante, id, extension));
        BufferedWriter bufferedWriter;
        try {
            String cpe,codigoTipoComprobanteElectronico;
            if(id.contains("FD")){
                cpe = "factura";
                codigoTipoComprobanteElectronico = "01";
            } else {
                cpe = "boleta";
                codigoTipoComprobanteElectronico = "03";
            }
            rs = NotaDebito.consulta("select * \n"
                    + "from notadebito \n"
                    + "inner join "+cpe+" \n"
                    + "on "+cpe+".id = notadebito.idComprobante \n"
                    + "inner join cliente \n"
                    + "on cliente.id = "+cpe+".idCliente \n"
                    + "where notadebito.id = '" + id + "';");
            String idComprobante = null;
            String fechaEmision = null;
            String horaEmision = null;
            String tipoDocumento = null;
            String numeroDocumento = null;
            String nombreRazonSocial = null;
            String moneda = null;
            String motivo = null;
            String igv = null;
            String totalVentasGravadas = null;
            String importeTotal = null;

            while (rs.next()) {
                idComprobante = rs.getString("idComprobante");
                fechaEmision = rs.getString("fecha");
                horaEmision = rs.getString("horaEmision");
                tipoDocumento = rs.getString("tipoDocumento");
                numeroDocumento = rs.getString("numeroDocumento");
                nombreRazonSocial = rs.getString("nombreRazonSocial");
                moneda = rs.getString("moneda");
                motivo = rs.getString("motivo");
                igv = rs.getString("igv");
                totalVentasGravadas = rs.getString("totalVentasGravadas");
                importeTotal = rs.getString("importeTotal");
            }
            rs.close();

            bufferedWriter = new BufferedWriter(new FileWriter(ap));
            //21 campos
            String tipOperacion = "0101"; //venta interna
            String fecEmision = Metodos.fechaFormatoSUNAT(fechaEmision);//YYYY-MM-DD
            String horEmision = horaEmision;
            String codLocalEmisor = "0000";//domicilio fiscal
            String tipDocUsuario = Catalogos.tipoDocumentoIdentidad("", tipoDocumento)[0];// DNI/canet ex/pass
            String numDocUsuario = numeroDocumento;
            String rznSocialUsuario = nombreRazonSocial;
            String tipMoneda = moneda;//PEN,USD
            String codMotivo = Catalogos.tipoNotaDebito("", motivo)[0];
            String desMotivo = motivo;
            String tipDocAfectado = codigoTipoComprobanteElectronico;
            String numDocAfectado = idComprobante;
            String sumTotTributos = igv;//igv
            String sumTotValVenta = totalVentasGravadas;
            String sumPrecioVenta = importeTotal;
            String sumDescTotal = "0.00";
            String sumOtrosCargos = "0.00";
            String sumTotalAnticipos = "0.00";
            String sumImpVenta = importeTotal;
            String ublVersionId = "2.1";
            String customizationId = "2.0";
            //se esccribe la linea en el archivo
            bufferedWriter.write(
                    tipOperacion + "|"
                    + fecEmision + "|"
                    + horEmision + "|"
                    + codLocalEmisor + "|"
                    + tipDocUsuario + "|"
                    + numDocUsuario + "|"
                    + rznSocialUsuario + "|"
                    + tipMoneda + "|"
                    + codMotivo + "|"
                    + desMotivo + "|"
                    + tipDocAfectado + "|"
                    + numDocAfectado + "|"
                    + sumTotTributos + "|"
                    + sumTotValVenta + "|"
                    + sumPrecioVenta + "|"
                    + sumDescTotal + "|"
                    + sumOtrosCargos + "|"
                    + sumTotalAnticipos + "|"
                    + sumImpVenta + "|"
                    + ublVersionId + "|"
                    + customizationId
            );
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error creando archivo plano " + id + "." + extension + ":\n" + e);
            //Metodos.MensajeError("Error creando archivo plano " + id + "."+extension+":\n" + e);
        }

    }
    
    private static void apNotaDebitoDet(String id, String codigoTipoComprobante, String extension) {
        File ap = new File(Rutas.getRutaAP(codigoTipoComprobante, id, extension));
        BufferedWriter bufferedWriter;
        try {
            rs = Boleta.consulta("select * \n"
                    + "from notadebitodet \n"
                    + "where idNotaDebito = '" + id + "';");
            bufferedWriter = new BufferedWriter(new FileWriter(ap));
            while (rs.next()) {
                String catidad = rs.getString("cantidad");
                String codigo = rs.getString("codigo");
                String descripcion = rs.getString("descripcion");
                String tributo = rs.getString("tributo");
                String montoTributo = rs.getString("montoTributo");
                String tipoAfectacionTributo = rs.getString("tipoAfectacionTributo");
                String valorUnitario = rs.getString("valorUnitario");
                String valorUnitarioGratuito = rs.getString("valorUnitarioGratuito");
                String precioUnitarioItem = rs.getString("precioUnitarioItem");
                
                //36 campos
                String codUnidadMedida = "EA";
                String ctdUnidadItem = catidad;
                String codProducto = codigo;
                String codProductoSUNAT = "-";
                String desItem = descripcion;
                String mtoValorUnitario = valorUnitario;
                String sumTotTributosItem = String.valueOf(Double.parseDouble(precioUnitarioItem) * 0.18);
                //========== Tributo IGV =============
                //Tributo: IGV(1000) - IVAP(1016) - EXP(9995) - GRA(9996) - EXO(9997) - INA(9998)
                String codTriIGV = Catalogos.tipoTributo("", "", "", tributo)[0];
                String mtoIgvItem = montoTributo;
                String mtoBaseIgvItem = Metodos.formatoDecimalMostrar(
                        (Metodos.formatoDecimalOperar(valorUnitario) + Metodos.formatoDecimalOperar(valorUnitarioGratuito))
                            * Metodos.formatoDecimalOperar(catidad)
                );
                String nomTributoIgvItem = Catalogos.tipoTributo("", "", "", tributo)[3];
                String codTipTributoIgvItem = Catalogos.tipoTributo("", "", "", tributo)[2];
                String tipAfeIGV = tipoAfectacionTributo;
                String porIgvItem = porcentajeIgv;
                //========== Tributo ISC =============
                //Tributo ISC (2000)
                String codTriISC = "-";
                String mtoIscItem = "";
                String mtoBaseIscItem = "";
                String nomTributoIscItem = "";
                String codTipTributoIscItem = "";
                String tipSisISC = "";
                String porIscItem = "";
                //========== Tributo otro ==============
                //Tributo Otro 9999
                String codTriOtro = "-";
                String mtoTriOtroItem = "";
                String mtoBaseTriOtroItem = "";
                String nomTributoOtroItem = "";
                String codTipTributoOtroItem = "";
                String porTriOtroItem = "";
                //========== Tributo ICBPER =============
                //Tributo ICBPER 7152
                String codTriIcbper = "-";
                String mtoTriIcbperItem = "";
                String ctdBolsasTriIcbperItem = "";
                String nomTributoIcbperItem = "";
                String codTipTributoIcbperItem = "";
                String mtoTriIcbperUnidad = "";
                // ======================================
                String mtoPrecioVentaUnitario;
                if (tributo.equals("IGV")) {
                    mtoPrecioVentaUnitario = String.valueOf(
                            ((Double.parseDouble(valorUnitario) + Metodos.formatoDecimalOperar(valorUnitarioGratuito))
                            * (Double.parseDouble(porcentajeIgv) / 100))
                            + (Double.parseDouble(valorUnitario) + Metodos.formatoDecimalOperar(valorUnitarioGratuito))
                    );
                } else { //GRA
                    mtoPrecioVentaUnitario = "0.00";
                }
                String mtoValorVentaItem = Metodos.formatoDecimalMostrar(
                        Metodos.formatoDecimalOperar(catidad)
                        * (Metodos.formatoDecimalOperar(valorUnitario) + Metodos.formatoDecimalOperar(valorUnitarioGratuito))
                );
                String mtoValorReferencialUnitario = valorUnitarioGratuito;

                bufferedWriter.write(
                        codUnidadMedida + "|"
                        + ctdUnidadItem + "|"
                        + codProducto + "|"
                        + codProductoSUNAT + "|"
                        + desItem + "|"
                        + mtoValorUnitario + "|"
                        + sumTotTributosItem + "|"
                        + codTriIGV + "|"
                        + mtoIgvItem + "|"
                        + mtoBaseIgvItem + "|"
                        + nomTributoIgvItem + "|"
                        + codTipTributoIgvItem + "|"
                        + tipAfeIGV + "|"
                        + porIgvItem + "|"
                        + codTriISC + "|"
                        + mtoIscItem + "|"
                        + mtoBaseIscItem + "|"
                        + nomTributoIscItem + "|"
                        + codTipTributoIscItem + "|"
                        + tipSisISC + "|"
                        + porIscItem + "|"
                        + codTriOtro + "|"
                        + mtoTriOtroItem + "|"
                        + mtoBaseTriOtroItem + "|"
                        + nomTributoOtroItem + "|"
                        + codTipTributoOtroItem + "|"
                        + porTriOtroItem + "|"
                        + codTriIcbper + "|"
                        + mtoTriIcbperItem + "|"
                        + ctdBolsasTriIcbperItem + "|"
                        + nomTributoIcbperItem + "|"
                        + codTipTributoIcbperItem + "|"
                        + mtoTriIcbperUnidad + "|"
                        + mtoPrecioVentaUnitario + "|"
                        + mtoValorVentaItem + "|"
                        + mtoValorReferencialUnitario + "|\n");
            }
            rs.close();
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error creando archivo plano " + id + "." + extension + ":\n" + e);
            //Metodos.MensajeError("Error creando archivo plano " + id + ".DET:\n" + e);
        }
    }
    
    private static boolean[] tributoNotaDebito(String id) {
        boolean[] tributos = {false, false, false, false, false, false, false, false, false};

        try {
            rs = NotaDebito.consulta("select * \n"
                    + "from notadebitodet \n"
                    + "where idNotaDebito = '" + id + "';");
            String tributo;

            while (rs.next()) {
                tributo = rs.getString("tributo");
                switch (tributo) {
                    case "IGV":
                        tributos[0] = true;
                        break;
                    case "IVAP":
                        tributos[1] = true;
                        break;
                    case "ISC":
                        tributos[2] = true;
                        break;
                    case "ICBPER":
                        tributos[3] = true;
                        break;
                    case "EXP":
                        tributos[4] = true;
                        break;
                    case "GRA":
                        tributos[5] = true;
                        break;
                    case "EXO":
                        tributos[6] = true;
                        break;
                    case "INA":
                        tributos[7] = true;
                        break;
                    default: // OTROS
                        tributos[8] = true;
                        break;
                }
            }
            rs.close();
        } catch (Exception e) {

        }
        return tributos;
    }
    
    private static void apNotaDebitoTri(String id, String codigoTipoComprobante, String extension, boolean tributos[]) {
        File ap = new File(Rutas.getRutaAP(codigoTipoComprobante, id, extension));
        BufferedWriter bufferedWriter;
        try {
            rs = NotaDebito.consulta("select * \n"
                    + "from notadebito \n"
                    + "where id = '" + id + "';");
            String igv = "0.00";
            String totalVentasGravadas = "0.00";
            String totalGratuito = "0.00";
            while (rs.next()) {
                igv = rs.getString("igv");
                totalVentasGravadas = rs.getString("totalVentasGravadas");
                totalGratuito = rs.getString("totalGratuito");
            }
            rs.close();
            
            bufferedWriter = new BufferedWriter(new FileWriter(ap));
            if (tributos[0] == true) { // IGV
                
                //5 campos
                String ideTributo = Catalogos.tipoTributo("1000", "", "", "")[0];
                String nomTributo = Catalogos.tipoTributo("1000", "", "", "")[3];
                String codTipTributo = Catalogos.tipoTributo("1000", "", "", "")[2];
                String mtoBaseImponible = totalVentasGravadas;
                String mtoTributo = igv;
                //se esccribe la linea en el archivo
                bufferedWriter.write(
                        ideTributo + "|"
                        + nomTributo + "|"
                        + codTipTributo + "|"
                        + mtoBaseImponible + "|"
                        + mtoTributo + "|\n");
                //bufferedWriter.close();
            }
            if (tributos[5] == true) { // GRA
                //bufferedWriter = new BufferedWriter(new FileWriter(ap));
                //5 campos
                String ideTributo = Catalogos.tipoTributo("9996", "", "", "")[0];
                String nomTributo = Catalogos.tipoTributo("9996", "", "", "")[3];
                String codTipTributo = Catalogos.tipoTributo("9996", "", "", "")[2];
                String mtoBaseImponible = totalGratuito;
                String mtoTributo = "0.00";
                //se esccribe la linea en el archivo
                bufferedWriter.write(
                        ideTributo + "|"
                        + nomTributo + "|"
                        + codTipTributo + "|"
                        + mtoBaseImponible + "|"
                        + mtoTributo + "|\n");
                bufferedWriter.close();
            }
            if (tributos[7] == true) {//INA 

            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error creando archivo plano " + id + "." + extension + ":\n" + e);
            //Metodos.MensajeError("Error creando archivo plano " + id + ".TRI:\n" + e);
        }

    }

    private static void apNotaDebitoLey(String id, String codigoTipoComprobante, String extension) {
        File ap = new File(Rutas.getRutaAP(codigoTipoComprobante, id, extension));
        BufferedWriter bufferedWriter;
        try {
            rs = Boleta.consulta("select * \n"
                    + "from notadebito \n"
                    + "where id = '" + id + "';");

            String moneda = null;
            double totalGratuito = 0.00;
            double importeTotal = 0.00;

            while (rs.next()) {
                moneda = rs.getString("moneda");
                totalGratuito = Double.parseDouble(rs.getString("totalGratuito"));
                importeTotal = Double.parseDouble(rs.getString("importeTotal"));
            }
            rs.close();
            bufferedWriter = new BufferedWriter(new FileWriter(ap));
            //2 campos
            String codLeyenda = Catalogos.tipoLeyenda("1000", "")[0];
            String desLeyenda = Metodos.convertirNumTexto(Metodos.formatoDecimalMostrar(importeTotal), moneda);
            //se escribe la linea en el archivo
            bufferedWriter.write(
                    codLeyenda + "|"
                    + desLeyenda + "|\n"
            );

            if (totalGratuito > 0.00 || importeTotal == 0.00) {
                // 1002 Leyenda "TRANSFERENCIA GRATUITA DE UN BIEN Y/O SERVICIO PRESTADO GRATUITAMENTE"
                //2 campos
                String codLeyendaGratuita = Catalogos.tipoLeyenda("1002", "")[0];
                String desLeyendaGratuita = Catalogos.tipoLeyenda("1002", "")[1];
                //se escribe la linea en el archivo
                bufferedWriter.write(
                        codLeyendaGratuita + "|"
                        + desLeyendaGratuita + "|\n"
                );
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error creando archivo plano " + id + "." + extension + ":\n" + e);
            //Metodos.MensajeError("Error creando archivo plano " + id + ".LEY:\n" + e);
        }

    }
    
    // ================= Baja ==================
    
    public static void apBaja(String id) {
        //codigo tipo comprobante
        apBajaCba(id);
    }

    private static void apBajaCba(String id) {
        File ap = new File(Rutas.getRutaAPBaja(id));
        BufferedWriter bufferedWriter;

        try {
            rs = Baja.consulta("select * \n"
                    + "from bajadet \n"
                    + "inner join baja \n"
                    + "on baja.id =  bajadet.idBaja \n"
                    + "where idBaja = '" + id + "';");
            bufferedWriter = new BufferedWriter(new FileWriter(ap));
            String fecha = null;
            String fechaComprobante = null;
            String tipoComprobante = null;
            String idComprobante = null;
            String motivo = null;
            
            while (rs.next()) {
                fecha = rs.getString("fecha");
                fechaComprobante = rs.getString("fechaComprobante");
                tipoComprobante = rs.getString("tipoComprobante");
                idComprobante = rs.getString("idComprobante");
                motivo = rs.getString("motivo");
            }
            rs.close();
            
            //5 campos
            String fecGeneracion = Metodos.fechaFormatoSUNAT(fechaComprobante);
            String fecComunicacion = Metodos.fechaFormatoSUNAT(fecha);
            String tipDocBaja = Catalogos.tipoDocumento("", tipoComprobante)[0];
            String numDocBaja = idComprobante;
            String desMotivoBaja = motivo;
            
            bufferedWriter.write(
                    fecGeneracion + "|"
                    + fecComunicacion + "|"
                    + tipDocBaja + "|"
                    + numDocBaja + "|"
                    + desMotivoBaja + "\n");
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error creando archivo plano " + id + ".CBA:\n" + e);
            //Metodos.MensajeError("Error creando archivo plano " + id + ".CBA:\n" + e);
        }

    }

    
    
    
    // de momento no se usa
    private void ArchivoPlanoACA() {
//        //se crea archivo con el nombre establecido
//        File ap = new File(Rutas.getRutaAP("01", "id", "ACA"));
//        BufferedWriter bufferedWriter;
//        if (ap.exists()) {
//            Metodos.MensajeError("Error generando archivo\n"
//                    + "plano '" + "id" + ".ACA':\n"
//                    + "El archivo ya existe");
//        } else {
//            try {
//                bufferedWriter = new BufferedWriter(new FileWriter(ap));
//                String cuenta_detraccion = "";
//                String codigo_producto_detraccion = "";
//                String porcentaje_detraccion = "";
//                String monto_detraccion = "";
//                //String medio_pago = Metodos.ObtenerCodigoMedioPago(jcbxMedioPago.getSelectedItem().toString());
//                //direccion cliente
//                String direccion_cliente_pais = "-";
//                String direccion_cliente_ubigeo = "-";
//                String direccion_cliente_detallada = "-";
//                //direccion distinta a la del cliente
//                String direccion_cliente_pais_distinta = "-";
//                String direccion_cliente_ubigeo_distinta = "-";
//                String direccion_cliente_detallada_distinta = "-";
//                //se esccribe la linea en el archivo
//                bufferedWriter.write(
//                        cuenta_detraccion + "|"
//                        + codigo_producto_detraccion + "|"
//                        + porcentaje_detraccion + "|"
//                        + monto_detraccion + "|"
//                        //+ medio_pago + "|"
//                        + direccion_cliente_pais + "|"
//                        + direccion_cliente_ubigeo + "|"
//                        + direccion_cliente_detallada + "|"
//                        + direccion_cliente_pais_distinta + "|"
//                        + direccion_cliente_ubigeo_distinta + "|"
//                        + direccion_cliente_detallada_distinta
//                );
//                bufferedWriter.close();
//            } catch (Exception e) {
////                System.out.println("Error creando archivo plano " + id + ".ACA:\n" + e);
////                Metodos.MensajeError("Error creando archivo plano " + id + ".ACA:\n" + e);
//            }
//        }
    }
    

}
