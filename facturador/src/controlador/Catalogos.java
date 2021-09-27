/*
 * Catálogos según el anexo V de SUNAT
 * Anexo N.° 8 - Catálogo de códigos
 */
package controlador;

public class Catalogos {
    
    // N° 01 - Código de tipo de documento (tipo comprobante electrónico)
    public static String[] tipoDocumento(String codigo, String descripcion) {
        if (codigo != "") {
            switch (codigo) {
                case "01":
                    descripcion = "Factura";
                    break;
                case "03":
                    descripcion = "Boleta de venta";
                    break;
                case "04":
                    descripcion = "Liquidación de compra";
                    break;
                case "05":
                    descripcion = "Boletos de Transporte Aéreo que emiten las "
                            + "Compañías de Aviación Comercial por el servicio de "
                            + "transporte aéreo regular de pasajeros, emitido de "
                            + "manera manual, mecanizada o por medios electrónicos (BME)";
                    break;
                case "06":
                    descripcion = "Carta de porte aéreo";
                    break;
                case "07":
                    descripcion = "Nota de crédito";
                    break;
                case "08":
                    descripcion = "Nota de débito";
                    break;
                case "09":
                    descripcion = "Guía de remisión remitente";
                    break;
                case "11":
                    descripcion = "Póliza emitida por las Bolsas de Valores";
                    break;
                case "12":
                    descripcion = "Ticket de máquina registradora";
                    break;
                case "13":
                    descripcion = "Documento emitido por bancos, instituciones "
                            + "financieras, crediticias y de seguros que se "
                            + "encuentren bajo el control de la Superintendencia "
                            + "de Banca y Seguros";
                    break;
                case "14":
                    descripcion = "Recibo servicios públicos";
                    break;
                case "15":
                    descripcion = "Boletos emitidos por el servicio de transporte "
                            + "terrestre regular urbano de pasajeros y el "
                            + "ferroviario público de pasajeros prestado en vía "
                            + "férrea local";
                    break;
                case "16":
                    descripcion = "Boleto de viaje emitido por las empresas de "
                            + "transporte público interprovincial de pasajeros";
                    break;
                case "18":
                    descripcion = "Documentos emitidos por las AFP";
                    break;
                case "19":
                    descripcion = "Boleto por atracciones y espectáculos públicos";
                    break;
                case "20":
                    descripcion = "Comprobante de retención";
                    break;
            }
        } else {
            switch (descripcion) {
                case "Factura":
                    codigo = "01";
                    break;
                case "Boleta de venta":
                    codigo = "03";
                    break;
                case "Liquidación de compra":
                    codigo = "04";
                    break;
                case "Nota de crédito":
                    codigo = "07";
                    break;
                case "Nota de débito":
                    codigo = "08";
                    break;
                case "Guía de remisión remitente":
                    codigo = "09";
                    break;
            }
        }
        String[] tipoDocumento = {codigo, descripcion};
        return tipoDocumento;
    }
    
    // N° 05 - Código de tipos de tributos y otros conceptos
    public static String[] tipoTributo(String codigo, String descripcion,
            String codigoInternacional, String nombre){
        
        if (codigo != "") {
            switch (codigo) {
                case "1000":
                    descripcion = "IGV Impuesto General a las Ventas";
                    codigoInternacional = "VAT";
                    nombre = "IGV";
                    break;
                case "1016":
                    descripcion = "Impuesto a la Venta Arroz Pilado";
                    codigoInternacional = "VAT";
                    nombre = "IVAP";
                    break;
                case "2000":
                    descripcion = "ISC Impuesto Selectivo al Consumo";
                    codigoInternacional = "EXC";
                    nombre = "ISC";
                    break;
                case "7152":
                    descripcion = "Impuesto a la bolsa plastica";
                    codigoInternacional = "OTH";
                    nombre = "ICBPER";
                    break;
                case "9995":
                    descripcion = "Exportación";
                    codigoInternacional = "FRE";
                    nombre = "EXP";
                    break;
                case "9996":
                    descripcion = "Gratuito";
                    codigoInternacional = "FRE";
                    nombre = "GRA";
                    break;
                case "9997":
                    descripcion = "Exonerado";
                    codigoInternacional = "VAT";
                    nombre = "EXO";
                    break;
                case "9998":
                    descripcion = "Inafecto";
                    codigoInternacional = "FRE";
                    nombre = "INA";
                    break;
                case "9999":
                    descripcion = "Otros tributos";
                    codigoInternacional = "OTH";
                    nombre = "OTROS";
                    break;
            }
        } else if (descripcion != "") {
            switch (descripcion) {
                case "IGV Impuesto General a las Ventas":
                    codigo = "1000";
                    codigoInternacional = "VAT";
                    nombre = "IGV";
                    break;
                case "Impuesto a la Venta Arroz Pilado":
                    codigo = "1016";
                    codigoInternacional = "VAT";
                    nombre = "IVAP";
                    break;
                case "ISC Impuesto Selectivo al Consumo":
                    codigo = "2000";
                    codigoInternacional = "EXC";
                    nombre = "ISC";
                    break;
                case "Impuesto a la bolsa plastica":
                    codigo = "7152";
                    codigoInternacional = "OTH";
                    nombre = "ICBPER";
                    break;
                case "Exportación":
                    codigo = "9995";
                    codigoInternacional = "FRE";
                    nombre = "EXP";
                    break;
                case "Gratuito":
                    codigo = "9996";
                    codigoInternacional = "FRE";
                    nombre = "GRA";
                    break;
                case "Exonerado":
                    codigo = "9997";
                    codigoInternacional = "VAT";
                    nombre = "EXO";
                    break;
                case "Inafecto":
                    codigo = "9998";
                    codigoInternacional = "FRE";
                    nombre = "INA";
                    break;
                case "Otros tributos":
                    codigo = "9999";
                    codigoInternacional = "OTH";
                    nombre = "OTROS";
                    break;
            }
        } else if (codigoInternacional != "") {

        } else { // nombre != ""
            switch (nombre) {
                case "IGV":
                    codigo = "1000";
                    descripcion = "IGV Impuesto General a las Ventas";
                    codigoInternacional = "VAT";
                    break;
                case "IVAP":
                    codigo = "1016";
                    descripcion = "Impuesto a la Venta Arroz Pilado";
                    codigoInternacional = "VAT";
                    break;
                case "ISC":
                    codigo = "2000";
                    descripcion = "ISC Impuesto Selectivo al Consumo";
                    codigoInternacional = "EXC";
                    break;
                case "ICBPER":
                    codigo = "7152";
                    descripcion = "Impuesto a la bolsa plastica";
                    codigoInternacional = "OTH";
                    break;
                case "EXP":
                    codigo = "9995";
                    descripcion = "Exportación";
                    codigoInternacional = "FRE";
                    break;
                case "GRA":
                    codigo = "9996";
                    descripcion = "Gratuito";
                    codigoInternacional = "FRE";
                    break;
                case "EXO":
                    codigo = "9997";
                    descripcion = "Exonerado";
                    codigoInternacional = "VAT";
                    break;
                case "INA":
                    codigo = "9998";
                    descripcion = "Inafecto";
                    codigoInternacional = "FRE";
                    break;
                case "OTROS":
                    codigo = "9999";
                    descripcion = "Otros tributos";
                    codigoInternacional = "OTH";
                    break;
            }
        }

        String[] tipoTributo = {codigo, descripcion, codigoInternacional, nombre};
        return tipoTributo;
    }
    
    // N° 06 - Código de tipo de documento de identidad
    public static String[] tipoDocumentoIdentidad(String codigo, String descripcion) {
        if (codigo != "") {
            switch (codigo) {
                case "0":
                    descripcion = "DOC.TRIB.NO.DOM.SIN.RUC";
                    break;
                case "1":
                    descripcion = "DNI"; //Documento Nacional de Identidad
                    break;
                case "4":
                    descripcion = "Carnet de extranjería";
                    break;
                case "6":
                    descripcion = "RUC"; //Registro Unico de Contributentes
                    break;
                case "7":
                    descripcion = "Pasaporte";
                    break;
            }
        } else { // descripcion != ""
            switch (descripcion) {
                case "DOC.TRIB.NO.DOM.SIN.RUC":
                    codigo = "0";
                    break;
                case "DNI":
                    codigo = "1"; //Documento Nacional de Identidad
                    break;
                case "Carnet de extranjería":
                    codigo = "4";
                    break;
                case "RUC":
                    codigo = "6"; //Registro Unico de Contributentes
                    break;
                case "Pasaporte":
                    codigo = "7";
                    break;
            }
        }
        String[] tipoDocumentoIdentidad = {codigo, descripcion};
        return tipoDocumentoIdentidad;
    }
    
    // N° 07 - Código de tipo de afectación del IGV
    public static String[] tipoAfectacionIgv(String codigo, String descripcion,
            String codigoTributo){
        
        if (codigo != "") {
            switch (codigo) {
                case "10":
                    descripcion = "Gravado - Operación Onerosa";
                    codigoTributo = "1000";
                    break;
                case "11":
                    descripcion = "Gravado – Retiro por premio";
                    codigoTributo = "u";
                    break;
                case "12":
                    descripcion = "Gravado – Retiro por donación";
                    codigoTributo = "9996";
                    break;
                case "13":
                    descripcion = "Gravado – Retiro ";
                    codigoTributo = "9996";
                    break;
                case "14":
                    descripcion = "Gravado – Retiro por publicidad";
                    codigoTributo = "9996";
                    break;
                case "15":
                    descripcion = "Gravado – Bonificaciones";
                    codigoTributo = "9996";
                    break;
                case "16":
                    descripcion = "Gravado – Retiro por entrega a trabajadores";
                    codigoTributo = "9996";
                    break;
                case "17":
                    descripcion = "Gravado - IVAP";
                    codigoTributo = "1016 o 9996";
                    break;
                case "20":
                    descripcion = "Exonerado - Operación Onerosa";
                    codigoTributo = "9997";
                    break;
                case "21":
                    descripcion = "Exonerado - Transferencia gratuita";
                    codigoTributo = "9996";
                    break;
                case "30":
                    descripcion = "Inafecto - Operación Onerosa";
                    codigoTributo = "9998";
                    break;
                case "31":
                    descripcion = "Inafecto – Retiro por Bonificación";
                    codigoTributo = "9996";
                    break;
                case "32":
                    descripcion = "Inafecto – Retiro";
                    codigoTributo = "9996";
                    break;
                case "33":
                    descripcion = "Inafecto – Retiro por Muestras Médicas";
                    codigoTributo = "9996";
                    break;
                case "34":
                    descripcion = "Inafecto - Retiro por Convenio Colectivo";
                    codigoTributo = "9996";
                    break;
                case "35":
                    descripcion = "Inafecto – Retiro por premio";
                    codigoTributo = "9996";
                    break;
                case "36":
                    descripcion = "Inafecto - Retiro por publicidad";
                    codigoTributo = "9996";
                    break;
                case "37":
                    descripcion = "Inafecto - Transferencia gratuita";
                    codigoTributo = "9996";
                    break;
                case "40":
                    descripcion = "Exportación de Bienes o Servicios";
                    codigoTributo = "9995 o 9996";
                    break;
            }
        } else if (descripcion != "") {

        } else { // codigoTributo != ""

        }

        String[] tipoAfectacionIgv = {codigo, descripcion, codigoTributo};
        return tipoAfectacionIgv;
        
    }
    
    // N° 08 - Código de tipos de sistema de cálculo del ISC	
    public static String[] tipoSistemaCalculoIsc(String codigo, String descripcion,
            String tasa){
        
        if (codigo != "") {
            switch (codigo) {
                case "01":
                    descripcion = "Sistema al valor (Apéndice IV, lit. A – T.U.O IGV e ISC)";
                    tasa = "1.00";
                    break;
                case "02":
                    descripcion = "Aplicación del Monto Fijo ( Sistema específico, bienes en el apéndice III, Apéndice IV, lit. B – T.U.O IGV e ISC)";
                    tasa = "2.00";
                    break;
                case "03":
                    descripcion = "Sistema de Precios de Venta al Público (Apéndice IV, lit. C – T.U.O IGV e ISC)";
                    tasa = "0.50";
                    break;
            }
        } else if (descripcion != "") {

        } else { // tasa != ""

        }

        String[] tipoSistemaCalculoIsc = {codigo, descripcion, tasa};
        return tipoSistemaCalculoIsc;
    }
    
    // N° 09 - Código del tipo de nota de crédito electrónica
    public static String[] tipoNotaCredito(String codigo, String descripcion){
        if (codigo != "") {
            switch (codigo) {
                case "01":
                    descripcion = "Anulación de la operación";
                    break;
                case "02":
                    descripcion = "Anulación por error en el RUC";
                    break;
                case "03":
                    descripcion = "Corrección por error en la descripción";
                    break;
                case "04":
                    descripcion = "Descuento global";
                    break;
                case "05":
                    descripcion = "Descuento por ítem";
                    break;
                case "06":
                    descripcion = "Devolución total";
                    break;
                case "07":
                    descripcion = "Devolución por ítem";
                    break;
                case "08":
                    descripcion = "Bonificación";
                    break;
                case "09":
                    descripcion = "Disminución en el valor";
                    break;
                case "10":
                    descripcion = "Otros Conceptos";
                    break;
                case "11":
                    descripcion = "Ajustes de operaciones de exportación";
                    break;
                case "12":
                    descripcion = "Ajustes afectos al IVAP";
                    break;
            }
        } else { // descripcion != ""
            switch (descripcion) {
                case "Anulación de la operación":
                    codigo = "01";
                    break;
                case "Anulación por error en el RUC":
                    codigo = "02";
                    break;
                case "Corrección por error en la descripción":
                    codigo = "03";
                    break;
                case "Descuento global":
                    codigo = "04";
                    break;
                case "Descuento por ítem":
                    codigo = "05";
                    break;
                case "Devolución total":
                    codigo = "06";
                    break;
                case "Devolución por ítem":
                    codigo = "07";
                    break;
                case "Bonificación":
                    codigo = "08";
                    break;
                case "Disminución en el valor":
                    codigo = "09";
                    break;
                case "Otros Conceptos":
                    codigo = "10";
                    break;
                case "Ajustes de operaciones de exportación":
                    codigo = "11";
                    break;
                case "Ajustes afectos al IVAP":
                    codigo = "12";
                    break;
            }
        }

        String[] tipoNotaCredito = {codigo, descripcion};
        return tipoNotaCredito;
    }
    
    // N° 10 - Código del tipo de nota de crédito electrónica
    public static String[] tipoNotaDebito(String codigo, String descripcion){
        if (codigo != "") {
            switch (codigo) {
                case "01":
                    descripcion = "Intereses por mora";
                    break;
                case "02":
                    descripcion = "Aumento en el valor";
                    break;
                case "03":
                    descripcion = "Penalidades/otros conceptos";
                    break;
                case "11":
                    descripcion = "Ajustes de operaciones de exportación";
                    break;
                case "12":
                    descripcion = "Ajustes afectos al IVAP";
                    break;
            }
        } else { // descripcion != ""
            switch (descripcion) {
                case "Intereses por mora":
                    codigo = "01";
                    break;
                case "Aumento en el valor":
                    codigo = "02";
                    break;
                case "Penalidades/otros conceptos":
                    codigo = "03";
                    break;
                case "Ajustes de operaciones de exportación":
                    codigo = "11";
                    break;
                case "Ajustes afectos al IVAP":
                    codigo = "12";
                    break;
            }
        }

        String[] tipoNotaCredito = {codigo, descripcion};
        return tipoNotaCredito;
    }
    
    // N° 51 - Código de tipo de operación
    public static String[] tipoOperacion(String codigo, String descripcion) {
        switch (codigo) {
            case "0101":
                descripcion = "Venta interna";//Factura, Boletas
                break;
            case "0112":
                descripcion = "Venta Interna - Sustenta Gastos Deducibles Persona Natural"; //Factura 
                break;
            case "0113":
                descripcion = "Venta Interna-NRUS"; //Boleta
                break;
            case "0200":
                descripcion = "Exportación de Bienes"; //Factura, Boletas
                break;
            case "0201":
                descripcion = "Exportación de Servicios – Prestación servicios realizados íntegramente en el país"; //Factura, Boletas
                break;
            case "0202":
                descripcion = "Exportación de Servicios – Prestación de servicios de hospedaje No Domiciliado"; // Factura, Boletas
                break;
            case "0203":
                descripcion = "Exportación de Servicios – Transporte de navieras"; //Factura, Boletas
                break;
            case "0204":
                descripcion = "Exportación de Servicios – Servicios  a naves y aeronaves de bandera extranjera"; //Factura, Boletas
                break;
            case "0205":
                descripcion = "Exportación de Servicios  - Servicios que conformen un Paquete Turístico"; //Factura, Boletas
                break;
            case "0206":
                descripcion = "Exportación de Servicios – Servicios complementarios al transporte de carga"; //Factura, Boletas
                break;
            case "0207":
                descripcion = "Exportación de Servicios – Suministro de energía eléctrica a favor de sujetos domiciliados en ZED"; //Factura, Boletas
                break;
            case "0208":
                descripcion = "Exportación de Servicios – Prestación servicios realizados parcialmente en el extranjero"; // Factura, Boletas
                break;
            case "0301":
                descripcion = "Operaciones con Carta de porte aéreo (emitidas en el ámbito nacional)"; //Factura, Boletas
                break;
            case "0302":
                descripcion = "Operaciones de Transporte ferroviario de pasajeros"; //Factura, Boletas
                break;
            case "0303":
                descripcion = "Operaciones de Pago de regalía petrolera"; //Factura, Boletas
                break;
            case "0401":
                descripcion = "Ventas no domiciliados que no califican como exportación"; //Factura, Boletas
                break;
            case "1001":
                descripcion = "Operación Sujeta a Detracción"; //Factura, Boletas
                break;
            case "1002":
                descripcion = "Operación Sujeta a Detracción- Recursos Hidrobiológicos"; // Factura, Boletas
                break;
            case "1003":
                descripcion = "Operación Sujeta a Detracción- Servicios de Transporte Pasajeros"; //Factura, Boletas
                break;
            case "1004":
                descripcion = "Operación Sujeta a Detracción- Servicios de Transporte Carga"; //Factura, Boletas
                break;
            case "2001":
                descripcion = "Operación Sujeta a Percepción"; //Factura, Boletas
                break;
        }
        String[] tipoOperacion = {codigo, descripcion};
        return tipoOperacion;
    }
    
    // N° 52 - Códigos de leyendas
    public static String[] tipoLeyenda(String codigo, String descripcion){
        switch (codigo) {
            case "1000":
                descripcion = "Monto en Letras";
                break;
            case "1002":
                descripcion = "TRANSFERENCIA GRATUITA DE UN BIEN Y/O SERVICIO PRESTADO GRATUITAMENTE";
                break;
            case "2000":
                descripcion = "COMPROBANTE DE PERCEPCIÓN";
                break;
            case "2006":
                descripcion = "Operación sujeta a detracción";
                break;
            case "2007":
                descripcion = "Operación sujeta al IVAP";
                break;
        }
        String[] tipoLeyenda = {codigo, descripcion};
        return tipoLeyenda;
    }
            
    
}
