import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.konsercb.R
import com.example.konsercb.database.Event
import com.example.konsercb.model.HomeViewModel
import com.example.konsercb.model.PenyediaViewModel
import com.example.konsercb.navigasi.DestinasiNavigasi
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.konsercb.navigasi.EventTopAppBar

object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = R.string.titleapp
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onDetailClick: (Int) -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            EventTopAppBar(
                title = stringResource(DestinasiHome.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.entry_event)
                )
            }
        }
    ) { innerpadding ->
        val uiStateEvent by viewModel.homeUiState.collectAsState()
        BodyHome(
            itemEvent = uiStateEvent.listEvent,
            modifier = Modifier
                .padding(innerpadding)
                .fillMaxSize(),
            onEventClick = onDetailClick
        )
    }
}

@Composable
fun BodyHome(
    itemEvent: List<Event>,
    modifier: Modifier = Modifier,
    onEventClick: (Int) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (itemEvent.isEmpty()) {
            Text(
                text = stringResource(id = R.string.deskripsi_no_item),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            ListEvent(
                itemEvent = itemEvent,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
                onItemClick = { onEventClick(it.id) }
            )
        }
    }
}

@Composable
fun ListEvent(
    itemEvent: List<Event>,
    modifier: Modifier = Modifier,
    onItemClick: (Event) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items = itemEvent, key = { it.id }) { event ->
            DataEvent(
                event = event,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(event) }
            )
        }
    }
}

@Composable
fun DataEvent(
    event: Event,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = event.namaevent,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                Icon(imageVector = Icons.Default.Phone, contentDescription = null)
                Text(
                    text = event.penyelenggaraevent,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = event.alamatevent,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = event.deskripsievent,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = event.waktuevent,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = event.tanggalevent,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
