package com.miho.springmongodbrecipeapp.converters

import com.miho.springmongodbrecipeapp.commands.NotesCommand
import com.miho.springmongodbrecipeapp.domain.Notes
import org.springframework.stereotype.Component

@Component
class NotesCommandToNotes : KotlinConverter<NotesCommand?, Notes?> {

    @Synchronized
    override fun convert(source: NotesCommand?): Notes? {

        if (source == null)
            return null

        return Notes(recipeNotes = source.recipeNotes, id = source.id)
    }
}