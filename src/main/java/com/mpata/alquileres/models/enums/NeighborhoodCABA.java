package com.mpata.alquileres.models.enums;

public enum NeighborhoodCABA {
    AGRONOMIA("Agronomia"),
    ALMAGRO("Almagro"),
    BALVANERA("Balvanera"),
    BARRACAS("Barracas"),
    BELGRANO("Belgrano"),
    BOEDO("Boedo"),
    CABALLITO("Caballito"),
    CHACARITA("Chacarita"),
    COGHLAN("Coghlan"),
    COLEGIALES("Colegiales"),
    CONSTITUCION("Constitucion"),
    FLORES("Flores"),
    FLORESTA("Floresta"),
    LA_BOCA("La Boca"),
    LA_PATERNAL("La Paternal"),
    LINIERS("Liniers"),
    MATADEROS("Mataderos"),
    MONTE_CASTRO("Monte Castro"),
    MONSERRAT("Monserrat"),
    NUEVA_POMPEYA("Nueva Pompeya"),
    NUNEZ("Nunez"),
    PALERMO("Palermo"),
    PARQUE_AVELLANEDA("Parque Avellaneda"),
    PARQUE_CHACABUCO("Parque Chacabuco"),
    PARQUE_CHAS("Parque Chas"),
    PARQUE_PATRICIOS("Parque Patricios"),
    PUERTO_MADERO("Puerto Madero"),
    RECOLETA("Recoleta"),
    RETIRO("Retiro"),
    SAAVEDRA("Saavedra"),
    SAN_CRISTOBAL("San Cristobal"),
    SAN_NICOLAS("San Nicolas"),
    SAN_TELMO("San Telmo"),
    VELEZ_SARFIELD("Velez Sarsfield"),
    VERSALLES("Versalles"),
    VILLA_CRESPO("Villa Crespo"),
    VILLA_DEL_PARQUE("Villa del Parque"),
    VILLA_DEVOTO("Villa Devoto"),
    VILLA_GENERAL_MITRE("Villa General Mitre"),
    VILLA_LUGANO("Villa Lugano"),
    VILLA_LURO("Villa Luro"),
    VILLA_ORTUZAR("Villa Ortuzar"),
    VILLA_PUEYRREDON("Villa Pueyrredon"),
    VILLA_REAL("Villa Real"),
    VILLA_RIACHUELO("Villa Riachuelo"),
    VILLA_SANTA_RITA("Villa Santa Rita"),
    VILLA_SOLDATI("Villa Soldati"),
    VILLA_URQUIZA("Villa Urquiza");

    private final String displayName;

    NeighborhoodCABA(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}