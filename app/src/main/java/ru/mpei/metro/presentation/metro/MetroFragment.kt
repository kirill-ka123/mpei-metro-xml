package ru.mpei.metro.presentation.metro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.mpei.metro.R
import ru.mpei.metro.common.Constants
import ru.mpei.metro.databinding.MetroFragmentBinding
import ru.mpei.metro.presentation.MainActivity
import ru.mpei.metro.presentation.metro.di.DaggerMetroFragmentComponent
import ru.mpei.metro.presentation.metro.di.MetroFragmentComponent

class MetroFragment : Fragment(R.layout.metro_fragment) {
    private var metroFragmentComponent: MetroFragmentComponent? = null
    private val activityComponent
        get() = (requireActivity() as MainActivity).activityComponent

    private val metroViewModel: MetroViewModel by viewModels {
        activityComponent.metroViewModelFactory()
    }

    private var _binding: MetroFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MetroFragmentBinding.inflate(inflater, container, false)
        val rootView = binding.root

        val activityComponent = (requireActivity() as MainActivity).activityComponent
        metroFragmentComponent = DaggerMetroFragmentComponent
            .builder()
            .activityComponent(activityComponent)
            .rootView(rootView)
            .metroViewModel(metroViewModel)
            .build()

        metroFragmentComponent?.onCreateViewListeners()?.forEach { listener ->
            listener.onCreateView(viewLifecycleOwner)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        metroViewModel.updateMetroGraph(Constants.DEFAULT_CITY_ID)
        val metroGraph = metroFragmentComponent?.metroGraphProvider()?.getMetroGraph()
        metroGraph?.let { binding.metroView.setMetroGraph(it) }
        metroFragmentComponent?.branchSplineConstructor()?.let {
            binding.metroView.setBranchSplineConstructor(it)
        }
        metroViewModel.selectedRoute.observe(viewLifecycleOwner) { route ->
            binding.metroView.setRoute(route)
        }
        metroViewModel.selectedStations.observe(viewLifecycleOwner) { selectedStations ->
            binding.metroView.setSelectedStations(selectedStations)
            metroGraph?.let { metroViewModel.getRoutes(it) }
            if (selectedStations.fromStation != null && selectedStations.toStation != null) {
                metroViewModel.insertHistoryRoute(selectedStations)
            }
        }
        metroViewModel.selectedComfortWeight.observe(viewLifecycleOwner) { _ ->
            metroGraph?.let { metroViewModel.getRoutes(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
