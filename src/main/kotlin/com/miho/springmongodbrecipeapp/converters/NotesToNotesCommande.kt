package com.miho.springmongodbrecipeapp.converters

import com.miho.springmongodbrecipeapp.commands.NotesCommand
import com.miho.springmongodbrecipeapp.domain.Notes
import org.springframework.stereotype.Component

@Component
class NotesToNotesCommand : KotlinConverter<Notes?, NotesCommand?> {

    @Synchronized
    override fun convert(source: Notes?): NotesCommand? {

        if (source == null)
            return null

        return NotesCommand(source.id, source.recipeNotes)

    }
}