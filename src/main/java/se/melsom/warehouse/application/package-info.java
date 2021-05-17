/**
 * <p>
 * Applikationen är uppdelad i moduler där en modul motsvarar en gruppering av funktionalitet.
 * Till exempel funktionalitet för att sammanställa och presentera lagersaldo hanteras av
 * modulen status ("stock inventory status" alternativt "stock on hand").
 * </p>
 * <p>
 * Modulernas design baseras på mönstret Presentation Model.
 * Typiskt med en klass PresentationModel som realiserar modulens logik och en klass View
 * som realiserar själva användargränssnittet mer eller mindre fritt från logik.
 * </p>
 * <p>
 * I vissa fall finns även specialiseringar av Swing-komponenter som antingen representerar del av
 * Presentation Model (tex TableModel som håller presenterat data) eller View (som tex dialoger eller
 * specialisering av CellRenderer).
 * </p>
 */
package se.melsom.warehouse.application;