package com.example.konsercb.ui

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.konsercb.R
import com.example.konsercb.model.EntryEventViewModel
import com.example.konsercb.model.PenyediaViewModel
import com.example.konsercb.navigasi.DestinasiNavigasi
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.konsercb.model.DetailEvent
import com.example.konsercb.model.TicketCategory
import com.example.konsercb.model.UIStateEvent
import com.example.konsercb.model.addTicketCategory
import com.example.konsercb.model.updateTicketCategory
import com.example.konsercb.navigasi.EventTopAppBar
import kotlinx.coroutines.launch
import java.util.Calendar


object DestinasiEntry : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = R.string.buatevent
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryEventScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EntryEventViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            EventTopAppBar(
                title = stringResource(DestinasiEntry.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerpadding ->
        EntryEventBody(
            uiStateEvent = viewModel.uiStateEvent,
            onEventValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveEvent()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerpadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EntryEventBody(
    uiStateEvent: UIStateEvent,
    onEventValueChange: (DetailEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        FormInputEvent(
            detailEvent = uiStateEvent.detailEvent,
            onValueChange = onEventValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = true,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.btn_submit))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FormInputEvent(
    detailEvent: DetailEvent,
    onValueChange: (DetailEvent) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        val namaeventState = remember { mutableStateOf(detailEvent.namaevent) }
        OutlinedTextField(
            value = namaeventState.value,
            onValueChange = {
                namaeventState.value = it
                onValueChange(detailEvent.copy(namaevent = it))
            },
            label = { Text(stringResource(id = R.string.namaeventreq)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        val alamateventState = remember { mutableStateOf(detailEvent.alamatevent) }
        OutlinedTextField(
            value = alamateventState.value,
            onValueChange = {
                alamateventState.value = it
                onValueChange(detailEvent.copy(alamatevent = it))
            },
            label = { Text(stringResource(id = R.string.alamatreq)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        val penyelenggaraeventState = remember { mutableStateOf(detailEvent.penyelenggaraevent) }
        OutlinedTextField(
            value = penyelenggaraeventState.value,
            onValueChange = {
                penyelenggaraeventState.value = it
                onValueChange(detailEvent.copy(penyelenggaraevent = it))
            },
            label = { Text(stringResource(id = R.string.penyelenggarareq)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        val deskripsieventState = remember { mutableStateOf(detailEvent.deskripsievent) }
        OutlinedTextField(
            value = deskripsieventState.value,
            onValueChange = {
                deskripsieventState.value = it
                onValueChange(detailEvent.copy(deskripsievent = it))
            },
            label = { Text(stringResource(id = R.string.deskripsieventreq)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        val waktueventState = remember { mutableStateOf(detailEvent.waktuevent) }
        OutlinedTextField(
            value = waktueventState.value,
            onValueChange = {
                waktueventState.value = it
                onValueChange(detailEvent.copy(waktuevent = it))
            },
            label = { Text(stringResource(id = R.string.waktueventreq)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Custom Date Picker Button
        DatePicker(onDateSelected = { selectedDate ->
            onValueChange(detailEvent.copy(tanggalevent = selectedDate))
        }, detailEvent = detailEvent)

        if (enabled) {
            Text(
                stringResource(id = R.string.required_field),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }

        Divider(
            thickness = dimensionResource(id = R.dimen.padding_small),
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium))
        )

        Button(
            onClick = {
                onValueChange(detailEvent.addTicketCategory())
            },
            enabled = enabled,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.btn_tambah_kategori))
        }

        // Menampilkan kategori tiket yang sudah ditambahkan
        detailEvent.ticketCategories.forEachIndexed { index, ticketCategory ->
            TicketCategoryInput(
                ticketCategory = ticketCategory,
                onValueChange = { updatedCategory ->
                    onValueChange(detailEvent.updateTicketCategory(index, updatedCategory))
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}




@Composable
fun DatePicker(
    onDateSelected: (String) -> Unit,
    detailEvent: DetailEvent,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val calendar = remember { Calendar.getInstance() }
    val date = remember { mutableStateOf(detailEvent.tanggalevent) }

    LaunchedEffect(detailEvent.tanggalevent) {
        date.value = detailEvent.tanggalevent
    }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                val selectedDate = "$day/${month + 1}/$year"
                date.value = selectedDate
                onDateSelected(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        OutlinedTextField(
            value = date.value,
            onValueChange = {},
            label = { Text("Tanggal") },
            singleLine = true,
            modifier = Modifier.weight(1f),
            enabled = false, // Disabled to make it look like static text
        )

        OutlinedButton(
            onClick = { datePickerDialog.show() },
            modifier = Modifier.size(48.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_date),
                    contentDescription = null,
                    modifier = Modifier.size(12.dp).background(Color.Black)
                )
                Text("Select Date")
            }
        }
    }
}



@Composable
fun TicketCategoryInput(
    ticketCategory: TicketCategory,
    onValueChange: (TicketCategory) -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = ticketCategory.category,
            onValueChange = { onValueChange(ticketCategory.copy(category = it)) },
            label = { Text("Category") },
            modifier = Modifier.weight(1f),
            singleLine = true
        )

        OutlinedTextField(
            value = ticketCategory.price,
            onValueChange = { onValueChange(ticketCategory.copy(price = it)) },
            label = { Text("Harga") },
            modifier = Modifier.weight(1f),
            singleLine = true
        )
    }
}
