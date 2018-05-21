package com.miho.springmongodbrecipeapp.converters

import com.miho.springmongodbrecipeapp.commands.NotesCommand
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NotesCommandToNotesTest {


    private val ID_VALUE = "1"
    private val RECIPE_NOTES = "Notes"


    private var converter: NotesCommandToNotes? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        converter = NotesCommandToNotes()
    }

    @Test
    @Throws(Exception::class)
    fun testNullParameter() {
        assertNull(converter!!.convert(null))
    }

    @Test
    @Throws(Exception::class)
    fun testEmptyObject() {
        assertNotNull(converter!!.convert(NotesCommand("", "")))
    }

    @Test
    @Throws(Exception::class)
    fun convert() {
//given
        val notesCommand = NotesCommand(ID_VALUE, RECIPE_NOTES)
//when
        val notes = converter!!.convert(notesCommand)
//then
        assertNotNull(notes)
        assertEquals(ID_VALUE, notes!!.id)
        assertEquals(RECIPE_NOTES, notes.recipeNotes)
    }


}