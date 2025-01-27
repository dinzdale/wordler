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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import wordler.composeapp.generated.resources.Res
import wordler.composeapp.generated.resources.menu_new_game
import wordler.composeapp.generated.resources.menu_reload_game
import wordler.composeapp.generated.resources.menu_save_game
import wordler.composeapp.generated.resources.menu_show_word

enum class MenuItem() {
    NewGame,
    ShowWord,
    SaveGame,
    ReloadGame,
}

@OptIn(ExperimentalResourceApi::class)
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
                    offset = DpOffset(-5.dp, 10.dp),
                    expanded = true,
                    onDismissRequest = {show = false}
                ) {
                    DropdownMenuItem({
                        collapseAndCall(MenuItem.NewGame)
                    }) {
                        Text(text = stringResource(Res.string.menu_new_game))
                    }
                    DropdownMenuItem({
                        collapseAndCall(MenuItem.SaveGame)
                    }) {
                        Text(text = stringResource(Res.string.menu_save_game))
                    }
                    DropdownMenuItem({
                        collapseAndCall(MenuItem.ShowWord)
                    }) {
                        Text(text = stringResource(Res.string.menu_show_word))
                    }
                    DropdownMenuItem({
                        collapseAndCall(MenuItem.ReloadGame)
                    }) {
                        Text(text = stringResource(Res.string.menu_reload_game))
                    }
                }
            }
        }
    }
}