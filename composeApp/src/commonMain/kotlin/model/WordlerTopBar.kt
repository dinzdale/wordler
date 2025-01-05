package model

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

enum class MenuItem(val title: String) {
    NewGame("New game"),
    SaveGame("Save game"),
    ReloadGame("Reload game"),
}

@Composable
fun WordlerTopBar(onDismiss: () -> Unit, onMenuItemSelected: (MenuItem) -> Unit) {
    TopAppBar() {
        Row(Modifier.fillMaxWidth().wrapContentHeight(), horizontalArrangement = Arrangement.End) {
            Text("Options")
            DropdownMenu(expanded = true, onDismissRequest = onDismiss) {
                DropdownMenuItem({ onMenuItemSelected(MenuItem.NewGame) }) {
                    Text(text = MenuItem.NewGame.title)
                }
                DropdownMenuItem({ onMenuItemSelected(MenuItem.SaveGame) }) {
                    Text(text = MenuItem.SaveGame.title)
                }
                DropdownMenuItem({ onMenuItemSelected(MenuItem.ReloadGame) }) {
                    Text(text = MenuItem.ReloadGame.title)
                }
            }
        }
    }
}