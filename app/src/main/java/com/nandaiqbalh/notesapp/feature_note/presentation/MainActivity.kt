package com.nandaiqbalh.notesapp.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nandaiqbalh.notesapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.nandaiqbalh.notesapp.feature_note.presentation.notes.NoteScreen
import com.nandaiqbalh.notesapp.feature_note.presentation.util.Screen
import com.nandaiqbalh.notesapp.feature_note.ui.theme.NotesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			NotesAppTheme {
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					val navController = rememberNavController()
					NavHost(
						navController = navController,
						startDestination = Screen.NotesScreen.route
					) {
						composable(
							Screen.NotesScreen.route
						) {
							NoteScreen(navController = navController)
						}

						composable(
							Screen.AddEditNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}",
							arguments = listOf(
								navArgument(
									name = "noteId"
								) {
									type = NavType.IntType
									defaultValue = -1
								},

								navArgument(
									name = "noteColor"
								) {
									type = NavType.IntType
									defaultValue = -1
								}
							),
						) {
							val color = it.arguments?.getInt("noteColor") ?: -1
							AddEditNoteScreen(
								navController = navController,
								noteColor = color
							)
						}

					}
				}
			}
		}
	}
}
