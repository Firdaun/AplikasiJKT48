package com.example.aplikasijkt48.data

import com.example.aplikasijkt48.R

data class MemberProfile(
    val imageResId: Int,
    val name: String,
    val team: String
)

object GalleryData {
    private val teamOrder = mapOf("passion" to 1, "dream" to 2, "love" to 3, "trainee" to 4)

    val photoProfile = listOf(
        // === TIM DREAM ===
        MemberProfile(imageResId = R.drawable.kabesha_adeline_wijaya,       name = "delynn",    team = "dream"),
        MemberProfile(imageResId = R.drawable.kabesha_amanda_sukma,         name = "amanda",    team = "dream"),
        MemberProfile(imageResId = R.drawable.kabesha_chelsea_davina,       name = "chelsea",   team = "dream"),
        MemberProfile(imageResId = R.drawable.kabesha_febriola_sinambela,   name = "olla",      team = "dream"),
        MemberProfile(imageResId = R.drawable.kabesha_freya_jayawardana,    name = "freya",     team = "dream"),
        MemberProfile(imageResId = R.drawable.kabesha_gabriela_abigail,     name = "ella",      team = "dream"),
        MemberProfile(imageResId = R.drawable.kabesha_gendis_mayrannisa,    name = "gendis",    team = "dream"),
        MemberProfile(imageResId = R.drawable.kabesha_gita_sekar_andarini,  name = "gita",      team = "dream"),
        MemberProfile(imageResId = R.drawable.kabesha_greesella_adhalia,    name = "greesel",   team = "dream"),
        MemberProfile(imageResId = R.drawable.kabesha_helisma_putri,        name = "eli",       team = "dream"),
        MemberProfile(imageResId = R.drawable.kabesha_jesslyn_elly,         name = "lyn",       team = "dream"),
        MemberProfile(imageResId = R.drawable.kabesha_nina_tutachia,        name = "nachia",    team = "dream"),
        MemberProfile(imageResId = R.drawable.kabesha_oline_manuel,         name = "oline",     team = "dream"),
        MemberProfile(imageResId = R.drawable.kabesha_marsha_lenathea,      name = "marsha",    team = "dream"),
        MemberProfile(imageResId = R.drawable.kabesha_shabilqis_naila,      name = "nala",      team = "dream"),

        // === TIM PASSION ===
        MemberProfile(imageResId = R.drawable.kabesha_abigail_rachel,       name = "aralie",    team = "passion"),
        MemberProfile(imageResId = R.drawable.kabesha_angelina_christy,     name = "christy",   team = "passion"),
        MemberProfile(imageResId = R.drawable.kabesha_catherina_vallencia,  name = "erine",     team = "passion"),
        MemberProfile(imageResId = R.drawable.kabesha_cornelia_vanisa,      name = "oniel",     team = "passion"),
        MemberProfile(imageResId = R.drawable.kabesha_dena_natalia,         name = "dena",      team = "passion"),
        MemberProfile(imageResId = R.drawable.kabesha_desy_natalia,         name = "desy",      team = "passion"),
        MemberProfile(imageResId = R.drawable.kabesha_feni_fitriyanti,      name = "feni",      team = "passion"),
        MemberProfile(imageResId = R.drawable.kabesha_jessica_chandra,      name = "jessi",     team = "passion"),
        MemberProfile(imageResId = R.drawable.kabesha_kathrina_irene,       name = "katrina",   team = "passion"),
        MemberProfile(imageResId = R.drawable.kabesha_lulu_salsabila,       name = "lulu",      team = "passion"),
        MemberProfile(imageResId = R.drawable.kabesha_michelle_levia,       name = "levi",      team = "passion"),
        MemberProfile(imageResId = R.drawable.kabesha_mutiara_azzahra,      name = "muthe",     team = "passion"),
        MemberProfile(imageResId = R.drawable.kabesha_raisha_syifa,         name = "raisha",    team = "passion"),
        MemberProfile(imageResId = R.drawable.kabesha_ribka_budiman,        name = "ribka",     team = "passion"),
        MemberProfile(imageResId = R.drawable.kabesha_victoria_kimberly,    name = "kimmy",     team = "passion"),

        // === TIM LOVE ===
        MemberProfile(imageResId = R.drawable.kabesha_alya_amanda,          name = "alya",      team = "love"),
        MemberProfile(imageResId = R.drawable.kabesha_anindya_ramadhani,    name = "anin",      team = "love"),
        MemberProfile(imageResId = R.drawable.kabesha_aurellia,             name = "lia",       team = "love"),
        MemberProfile(imageResId = R.drawable.kabesha_aurhel_alana,         name = "lana",      team = "love"),
        MemberProfile(imageResId = R.drawable.kabesha_cathleen_nixie,       name = "cathy",     team = "love"),
        MemberProfile(imageResId = R.drawable.kabesha_celline_thefani,      name = "elin",      team = "love"),
        MemberProfile(imageResId = R.drawable.kabesha_cynthia_yaputera,     name = "cynthia",   team = "love"),
        MemberProfile(imageResId = R.drawable.kabesha_fiony_alveria,        name = "fiony",     team = "love"),
        MemberProfile(imageResId = R.drawable.kabesha_fritzy_rosmerian,     name = "fritzy",    team = "love"),
        MemberProfile(imageResId = R.drawable.kabesha_grace_octaviani,      name = "gracie",    team = "love"),
        MemberProfile(imageResId = R.drawable.kabesha_hillary_abigail,      name = "lily",      team = "love"),
        MemberProfile(imageResId = R.drawable.kabesha_indah_cahya,          name = "indah",     team = "love"),
        MemberProfile(imageResId = R.drawable.kabesha_jazzlyn_trisha,       name = "trisha",    team = "love"),
        MemberProfile(imageResId = R.drawable.kabesha_michelle_alexandra,   name = "michie",    team = "love"),
        MemberProfile(imageResId = R.drawable.kabesha_nayla_suji,           name = "nayla",     team = "love"),

        // === TIM TRAINEE ===
        MemberProfile(imageResId = R.drawable.kabesha_astrella_virgiananda, name = "virgi",     team = "trainee"),
        MemberProfile(imageResId = R.drawable.kabesha_aulia_riza,           name = "auwia",     team = "trainee"),
        MemberProfile(imageResId = R.drawable.kabesha_bong_aprilli,         name = "rily",      team = "trainee"),
        MemberProfile(imageResId = R.drawable.kabesha_hagia_sopia,          name = "gia",       team = "trainee"),
        MemberProfile(imageResId = R.drawable.kabesha_humaira_ramadhani,    name = "maira",     team = "trainee"),
        MemberProfile(imageResId = R.drawable.kabesha_jacqueline_immanuela, name = "ekin",      team = "trainee"),
        MemberProfile(imageResId = R.drawable.kabesha_jemima_evodie,        name = "jemima",    team = "trainee"),
        MemberProfile(imageResId = R.drawable.kabesha_mikaela_kusjanto,     name = "mikaela",   team = "trainee"),
        MemberProfile(imageResId = R.drawable.kabesha_nur_intan,            name = "intan",     team = "trainee")
    ).sortedWith(
        // 2. Terjemahan logikamu: Urutkan Tim dulu, baru urutkan Nama A-Z
        compareBy<MemberProfile> { teamOrder[it.team] ?: 5 }
            .thenBy { it.name }
    )
}