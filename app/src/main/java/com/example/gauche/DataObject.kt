package com.example.gauche

import com.example.gauche.database.component.*
import com.example.gauche.database.listComponent.ListComponent
import com.example.gauche.database.relevesCapteurs.RelevesCapteurs

data class DataObject(val listListComponent: List<ListComponent>,
                      val listListTypeAlerte: List<ListeTypeAlerte>,
                      val listAlerte:List<Alerte>,
                      val listBac:List<Bac>,
                      val listListeBacPosition: List<ListeBacPosition>,
                      val listComposant:List<Component>,
                      val listAlerteRecu: List<AlerteRecu>,
                      val listRelevesCapteurs: List<RelevesCapteurs>) {

}
