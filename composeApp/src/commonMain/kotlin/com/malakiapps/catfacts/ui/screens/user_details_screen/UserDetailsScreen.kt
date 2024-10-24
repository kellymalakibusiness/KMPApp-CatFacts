package com.malakiapps.catfacts.ui.screens.user_details_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import catfacts.composeapp.generated.resources.Res
import catfacts.composeapp.generated.resources.user_profile
import coil3.compose.rememberAsyncImagePainter
import com.malakiapps.catfacts.domain.UserDetailsViewModel
import com.malakiapps.catfacts.ui.screens.common.TopBarOnlyScaffold
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun UserDetailsScreen(navHostController: NavHostController, modifier: Modifier = Modifier) {
    val userDetailsViewModel: UserDetailsViewModel = koinInject()
    var name by rememberSaveable { mutableStateOf("") }
    val image by userDetailsViewModel.userProfileImage.collectAsState()

    LaunchedEffect(key1 = true){
        name = userDetailsViewModel.getCurrentUsername()
    }
    TopBarOnlyScaffold(
        title = "User details",
        onBackPress = {
            navHostController.navigateUp()
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfilePictureSelector(
                image = image,
                onEdit = {
                    userDetailsViewModel.updateUserProfileImage()
                },
                modifier = Modifier.padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                placeholder = {
                    Text(
                        text = "Name",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    userDetailsViewModel.saveUserDetails(
                        name = name
                    )
                    navHostController.navigateUp()
                },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.onSurface,
                    contentColor = MaterialTheme.colors.surface
                ),
                shape = CircleShape
            ){
                Text("Save")
            }
        }
    }
}

@Composable
private fun ProfilePictureSelector(image: ByteArray?, onEdit: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable {
            onEdit()
            }
            .padding(8.dp)
    ) {
        val painter = if (image != null){
            rememberAsyncImagePainter(model = image)
        } else {
            painterResource(Res.drawable.user_profile)
        }
        Image(
            painter = painter,
            contentDescription = "User profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Edit",
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.body1
        )
    }
}