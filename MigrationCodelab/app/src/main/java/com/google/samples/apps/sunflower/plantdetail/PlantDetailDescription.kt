/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.plantdetail

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.core.text.HtmlCompat
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel

@Composable
fun PlantDetailDescription(plantDetailViewModel: PlantDetailViewModel = viewModel()) {
    // Observes values coming from the VM's LiveData<Plant> field
    val plant by plantDetailViewModel.plant.observeAsState()

    // If plant is not null, display the content
    plant?.let {
        PlantDetailContent(it)
    }
}

@Composable
fun PlantDetailContent(plant: Plant) {
    Column {
        PlantName(plant.name)
        PlantWatering(plant.wateringInterval)
        PlantDescription(plant.description)
    }
}

@Composable
fun PlantName(name: String) {
    Text(
        style = MaterialTheme.typography.h5,
        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.margin_small))
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally),
        text = name
    )
}

@Composable
fun PlantWatering(wateringInterval: Int) {
    val normalPadding = dimensionResource(R.dimen.margin_normal)
    val centerWithPaddingModifier = Modifier
        .padding(horizontal = dimensionResource(R.dimen.margin_small))

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            color = MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold,
            modifier = centerWithPaddingModifier
                .padding(top = normalPadding),
            text = stringResource(R.string.watering_needs_prefix)
        )

        Text(
            text = LocalContext.current.resources.getQuantityString(
                R.plurals.watering_needs_suffix,
                wateringInterval, wateringInterval
            ),
            modifier = centerWithPaddingModifier
                .padding(bottom = normalPadding),
        )
    }
}

// <TextView
// android:id="@+id/plant_description"
// style="?android:attr/textAppearanceMedium"
// android:layout_width="0dp"
// android:layout_height="wrap_content"
// android:layout_marginStart="@dimen/margin_small"
// android:layout_marginTop="@dimen/margin_small"
// android:layout_marginEnd="@dimen/margin_small"
// android:minHeight="@dimen/plant_description_min_height"
// app:layout_constraintEnd_toEndOf="parent"
// app:layout_constraintStart_toStartOf="parent"
// app:layout_constraintTop_toBottomOf="@id/compose_view"
// app:renderHtml="@{viewModel.plant.description}"
// tools:text="Details about the plant" />
@Composable
private fun PlantDescription(description: String) {

    val htmlDescription = remember(description) {
        HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }
//    view.movementMethod = LinkMovementMethod.getInstance()
    AndroidView(
        factory = { context ->
            TextView(context).apply {
                movementMethod = LinkMovementMethod.getInstance()
            }
        },
        update = {
            it.text = htmlDescription
        }
    )
}

@Preview
@Composable
private fun PlantNamePreview() {
    MaterialTheme {
        val plant = Plant("id", "Apple", "description", 3, 30, "")
        PlantDetailContent(plant)
    }
}
