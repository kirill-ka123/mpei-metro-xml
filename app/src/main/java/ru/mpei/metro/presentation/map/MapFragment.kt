package ru.mpei.metro.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.mpei.metro.R
import ru.mpei.metro.common.Constants
import ru.mpei.metro.databinding.MapFragmentBinding
import ru.mpei.metro.presentation.MainActivity
import ru.mpei.metro.presentation.map.di.DaggerMapFragmentComponent
import ru.mpei.metro.presentation.map.di.MapFragmentComponent

class MapFragment : Fragment(R.layout.map_fragment) {
    private var mapFragmentComponent: MapFragmentComponent? = null
    private val activityComponent
        get() = (requireActivity() as MainActivity).activityComponent

    private val mapViewModel: MapViewModel by viewModels {
        activityComponent.mapViewModelFactory()
    }

    private var _binding: MapFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MapFragmentBinding.inflate(inflater, container, false)
        val rootView = binding.root

        val activityComponent = (requireActivity() as MainActivity).activityComponent
        mapFragmentComponent = DaggerMapFragmentComponent
            .builder()
            .activityComponent(activityComponent)
            .rootView(rootView)
            .mapViewModel(mapViewModel)
            .build()

        mapFragmentComponent?.onCreateViewListeners()?.forEach { listener ->
            listener.onCreateView(viewLifecycleOwner)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapViewModel.updateMetroGraph(Constants.DEFAULT_CITY_ID)
        mapFragmentComponent?.metroGraphProvider()?.let {
            binding.metroView.setMetroGraph(it.getMetroGraph())
        }
        mapViewModel.selectedRoute.observe(viewLifecycleOwner) { route ->
            binding.metroView.setRoute(route)
        }
        mapViewModel.selectedStations.observe(viewLifecycleOwner) { selectedStations ->
            binding.metroView.setSelectedStations(selectedStations)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
