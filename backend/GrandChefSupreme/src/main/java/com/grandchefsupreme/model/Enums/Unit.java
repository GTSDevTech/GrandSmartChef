package com.grandchefsupreme.model.Enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Unit {
    // Peso
    GRAMO("g"),
    KILOGRAMO("kg"),
    ONZA("oz"),
    LIBRA("lb"),

    // Volumen líquido
    MILILITRO("ml"),
    CENTILITRO("cl"),
    LITRO("l"),
    TAZA("taza"),
    CUCHARADA("cda"),
    CUCHARADITA("cdta"),
    CUCHARADA_POSTRE("cda postre"),
    CUCHARADA_SOPERA("cda sopera"),
    VASO("vaso"),
    COPA("copa"),
    CHORRO("chorro"),
    HILO("hilo"),
    HILERA("hilera"),

    // Piezas / conteo
    UNIDAD("u"),
    PIEZA("pieza"),
    DIENTE("diente"),
    HOJA("hoja"),
    RAMA("rama"),
    MANOJO("manojo"),
    PUÑADO("puñado"),
    PIZCA("pizca"),
    PUNTA("punta"),
    RODAJA("rodaja"),
    REBANADA("rebanada"),
    CUARTO("1/4"),
    MITAD("1/2"),
    TROZO("trozo"),
    LONCHA("loncha"),
    LAMINA("lámina"),

    // Envases
    LATA("lata"),
    BOTE("bote"),
    BOTELLA("botella"),
    FRASCO("frasco"),
    SOBRE("sobre"),
    BOLSA("bolsa"),
    PAQUETE("paquete"),
    CARTUCHO("cartucho"),

    // Caseras
    PLATO("plato"),
    BOL("bol"),
    CUENCO("cuenco"),
    TAZON("tazón"),
    FUENTE("fuente"),
    BANDEJA("bandeja"),
    MOLDE("molde"),

    // Tiempo
    MINUTO("min"),
    HORA("h"),
    SEGUNDO("s"),

    // Otras
    GOTA("gota"),
    PELICULA("película"),
    CAPA("capa"),
    NUCLEO("núcleo"),
    GAMAJO("gamajo"),
    CUERPO("cuerpo"),
    CABEZA("cabeza"),
    RACIMO("racimo"),
    MAZORCA("mazorca"),
    MAZO("mazo"),
    ATADO("atado"),
    RAMILLETE("ramillete"),
    BULBO("bulbo"),
    TUBERCULO("tubérculo"),
    UNIDADES("unidades");

    private final String label;

    Unit(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}