package model

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

enum class MenuItem(val title: String) {
    NewGame("New game"),
    ShowWord("Show word"),
    SaveGame("Save game"),
    ReloadGame("Reload game"),
}

@Composable
fun WordlerTopBar(onDismiss: () -> Unit, onMenuItemSelected: (MenuItem) -> Unit) {
    var show by remember { mutableStateOf(false) }


    fun collapseAndCall(menuItem: MenuItem) {
        show = false
        onMenuItemSelected(menuItem)
    }

    TopAppBar() {
        Row(
            Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalArrangement = Arrangement.End
        ) {
            Text("Options", modifier = Modifier.clickable { show = true })
            if (show) {
                DropdownMenu(
                    offset = DpOffset(-150.dp, 10.dp),
                    expanded = true,
                    onDismissRequest = {show = false}
                ) {
                    DropdownMenuItem({
                        collapseAndCall(MenuItem.NewGame)
                    }) {
                        Text(text = MenuItem.NewGame.title)
                    }
                    DropdownMenuItem({
                        collapseAndCall(MenuItem.SaveGame)
                    }) {
                        Text(text = MenuItem.SaveGame.title)
                    }
                    DropdownMenuItem({
                        collapseAndCall(MenuItem.ShowWord)
                    }) {
                        Text(text = MenuItem.ShowWord.title)
                    }
                    DropdownMenuItem({
                        collapseAndCall(MenuItem.ReloadGame)
                    }) {
                        Text(text = MenuItem.ReloadGame.title)
                    }
                }
            }
        }
    }
}