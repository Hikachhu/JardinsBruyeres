package fr.JardinBruyere.gauche.ui.aPropos

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.JardinBruyere.gauche.databinding.FragmentAproposBinding
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.TextView
import fr.JardinBruyere.gauche.R

class AProposFragment : Fragment() {

    private var _binding: FragmentAproposBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAproposBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val text:TextView=root.findViewById(R.id.textView2)
        val textAffiche="Cap projet réalisé par Florian MORIN, Aboubakar NFONCHIGBA, Kilian RANNOU, Moustapha SY, Vincent TOLAZZI, Yann VU QUOCK KHANH.<br></br>" +
                "Projet commandité par Philippe GRILLAS et supervisé par Vlad VALICA au cours de l\'année 2021-2022 pendant notre 4ème année à l\'ESIEA.<br></br>" +
                "Le github du projet est disponible<a href=\"https://github.com/JardinsBruyere\"> ici</a>"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            text.text = Html.fromHtml(textAffiche, Html.FROM_HTML_MODE_COMPACT);
        } else {
            text.text = Html.fromHtml(textAffiche);
        }
        text.movementMethod = LinkMovementMethod.getInstance();
        Log.e("Passage","fin recycler view A propos")
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}