package com.dhmaciel.githubstars.home.domain.mapper

import com.dhmaciel.githubstars.home.domain.repository.model.GitListResponse
import com.dhmaciel.githubstars.utils.fromJson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@Suppress("ClassName")
internal class GitListMapperTest {

    @DisplayName("Given api return Git List Values")
    @Nested
    inner class GitRepositories {
        @Test
        @DisplayName("When mapper returned object Should have GitList domain data")
        fun parseGitListResponseToGitListDomain() {
            val gitListResponse =
                fromJson<GitListResponse>("gitlist/git_list_success_payload.json")

            val gitList = gitListResponse.toDomain()

            assertNotNull(gitList)
            assertEquals(gitListResponse.totalCount, gitList.totalCount)
            assertEquals(gitListResponse.items.size, gitList.items.size)
        }
    }
}