package fr.JardinBruyere.gauche

data class Result (val total_count: Int, val incomplete_results: Boolean, val items: List<User>)