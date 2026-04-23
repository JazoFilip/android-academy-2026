import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.androidacademyapi.AppContainer
import com.example.androidacademyapi.ui.productdetails.ProductDetailsUIState
import com.example.androidacademyapi.ui.productdetails.ProductDetailsViewModel
import com.example.androidacademyapi.ui.productdetails.ProductDetailsViewModelFactory

@Composable
fun ProductDetailsScreen(navController: NavController, productId: Int) {
    val viewModel: ProductDetailsViewModel =
        viewModel(factory = ProductDetailsViewModelFactory(
            AppContainer.productRepository,
            productId
        )
        )

    ProductDetailsContent(
        uiState = viewModel.uiState.value,
        onNavigateBack = { navController.popBackStack() },
        onRetry = {viewModel.retryFetch()}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsContent(
    uiState: ProductDetailsUIState,
    onNavigateBack: () -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    val title = if (uiState is ProductDetailsUIState.Success) uiState.product.title else "Details"
                    Text(title, style = MaterialTheme.typography.titleMedium)
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState) {
                is ProductDetailsUIState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is ProductDetailsUIState.NoInternet -> {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.CloudOff,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Nema internetske veze",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Molimo provjerite postavke mreže i pokušajte ponovno.",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(onClick = onRetry) {
                            Text("Pokušaj ponovno")
                        }
                    }
                }
                is ProductDetailsUIState.Error -> {
                    Text(
                        text = "Error: ${uiState.message}",
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                is ProductDetailsUIState.Success -> {
                    val product = uiState.product
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = product.thumbnail,
                            contentDescription = product.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = product.title,
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = product.description ?: "No description available",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}