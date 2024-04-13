package ru.mpei.metro.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.mpei.metro.MetroApplication
import ru.mpei.metro.R
import ru.mpei.metro.data.calculateRoute
import ru.mpei.metro.data.mock.CityMock
import ru.mpei.metro.data.mock.StationMock
import ru.mpei.metro.databinding.MapFragmentBinding
import ru.mpei.metro.presentation.MainActivity
import ru.mpei.metro.presentation.map.di.DaggerMapFragmentComponent
import ru.mpei.metro.presentation.map.di.MapFragmentComponent
import javax.inject.Inject

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
            .city(CityMock.MOSCOW.value)
            .build()

        mapFragmentComponent?.onCreateViewListeners()?.forEach { listener ->
            listener.onCreateView(viewLifecycleOwner)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.metroView.setCity(CityMock.MOSCOW.value)
        mapViewModel.route.observe(viewLifecycleOwner) { route ->
            route?.let { binding.metroView.setRoute(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
