/*
 * This file is part of LuckPerms, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package me.lucko.luckperms.api;

import me.lucko.luckperms.api.manager.GroupManager;
import me.lucko.luckperms.api.manager.TrackManager;
import me.lucko.luckperms.api.manager.UserManager;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * A means of loading and saving permission data to/from the backend.
 *
 * @since 2.14
 */
public interface Storage {

    /**
     * Get the name of the storage implementation.
     *
     * @return the name of the implementation
     */
    @NonNull
    String getName();

    /**
     * Gets whether the storage instance is allowing logins on the platform.
     *
     * @return true if logins are enabled
     * @deprecated as this method always returns true.
     */
    @Deprecated
    boolean isAcceptingLogins();

    /**
     * Saves an action to storage
     *
     * @param entry the log entry to be saved
     * @return true if the operation completed successfully.
     * @throws NullPointerException if entry is null
     * @deprecated in favour of {@link ActionLogger#submit(LogEntry)}.
     */
    @NonNull
    @Deprecated
    CompletableFuture<Boolean> logAction(@NonNull LogEntry entry);

    /**
     * Loads and returns the entire log from storage
     *
     * @return a log instance, could be null if loading failed
     * @deprecated in favour of {@link ActionLogger#getLog()}
     */
    @NonNull
    @Deprecated
    CompletableFuture<Log> getLog();

    /**
     * Loads a user's data from the main storage into the plugins local storage.
     *
     * @param uuid     the uuid of the user to load
     * @param username the users username, or null if it is not known.
     * @return if the operation completed successfully
     * @throws NullPointerException if uuid is null
     * @deprecated in favour of {@link UserManager#loadUser(UUID, String)}
     */
    @NonNull
    @Deprecated
    CompletableFuture<Boolean> loadUser(@NonNull UUID uuid, @Nullable String username);

    /**
     * Loads a user's data from the main storage into the plugins local storage.
     *
     * @param uuid the uuid of the user to load
     * @return if the operation completed successfully
     * @throws NullPointerException if uuid is null
     * @deprecated in favour of {@link UserManager#loadUser(UUID)}
     */
    @Deprecated
    default @NonNull CompletableFuture<Boolean> loadUser(@NonNull UUID uuid) {
        return loadUser(uuid, null);
    }

    /**
     * Saves a user object back to storage.
     *
     * <p>You should call this after you make any changes to a user.</p>
     *
     * @param user the user to save
     * @return true if the operation completed successfully.
     * @throws NullPointerException  if user is null
     * @throws IllegalStateException if the user instance was not obtained from LuckPerms.
     * @deprecated in favour of {@link UserManager#saveUser(User)}
     */
    @NonNull
    @Deprecated
    CompletableFuture<Boolean> saveUser(@NonNull User user);

    /**
     * Gets a set all "unique" user UUIDs.
     *
     * <p>"Unique" meaning the user isn't just a member of the "default" group.</p>
     *
     * @return a set of uuids, or null if the operation failed.
     * @deprecated in favour of {@link UserManager#getUniqueUsers()}
     */
    @NonNull
    @Deprecated
    CompletableFuture<Set<UUID>> getUniqueUsers();

    /**
     * Searches for a list of users with a given permission.
     *
     * @param permission the permission to search for
     * @return a list of held permissions, or null if the operation failed
     * @throws NullPointerException if the permission is null
     * @since 2.17
     * @deprecated in favour of {@link UserManager#getWithPermission(String)}
     */
    @NonNull
    @Deprecated
    CompletableFuture<List<HeldPermission<UUID>>> getUsersWithPermission(@NonNull String permission);

    /**
     * Creates and loads a group into the plugins local storage
     *
     * @param name the name of the group
     * @return true if the operation completed successfully
     * @throws NullPointerException     if name is null
     * @throws IllegalArgumentException if the name is invalid
     * @deprecated in favour of {@link GroupManager#createAndLoadGroup(String)}
     */
    @NonNull
    @Deprecated
    CompletableFuture<Boolean> createAndLoadGroup(@NonNull String name);

    /**
     * Loads a group into the plugins local storage.
     *
     * @param name the name of the group
     * @return true if the operation completed successfully
     * @throws NullPointerException     if name is null
     * @throws IllegalArgumentException if the name is invalid
     * @deprecated in favour of {@link GroupManager#loadGroup(String)}
     */
    @NonNull
    @Deprecated
    CompletableFuture<Boolean> loadGroup(@NonNull String name);

    /**
     * Loads all groups from the storage into memory
     *
     * @return true if the operation completed successfully.
     * @deprecated in favour of {@link GroupManager#loadAllGroups()}
     */
    @NonNull
    @Deprecated
    CompletableFuture<Boolean> loadAllGroups();

    /**
     * Saves a group back to storage.
     *
     * <p>You should call this after you make any changes to a group.</p>
     *
     * @param group the group to save
     * @return true if the operation completed successfully.
     * @throws NullPointerException  if group is null
     * @throws IllegalStateException if the group instance was not obtained from LuckPerms.
     * @deprecated in favour of {@link GroupManager#saveGroup(Group)}
     */
    @NonNull
    @Deprecated
    CompletableFuture<Boolean> saveGroup(@NonNull Group group);

    /**
     * Permanently deletes a group from storage.
     *
     * @param group the group to delete
     * @return true if the operation completed successfully.
     * @throws NullPointerException  if group is null
     * @throws IllegalStateException if the group instance was not obtained from LuckPerms.
     * @deprecated in favour of {@link GroupManager#deleteGroup(Group)}
     */
    @NonNull
    @Deprecated
    CompletableFuture<Boolean> deleteGroup(@NonNull Group group);

    /**
     * Searches for a list of groups with a given permission.
     *
     * @param permission the permission to search for
     * @return a list of held permissions, or null if the operation failed
     * @throws NullPointerException if the permission is null
     * @since 2.17
     * @deprecated in favour of {@link GroupManager#getWithPermission(String)}
     */
    @NonNull
    @Deprecated
    CompletableFuture<List<HeldPermission<String>>> getGroupsWithPermission(@NonNull String permission);

    /**
     * Creates and loads a track into the plugins local storage
     *
     * @param name the name of the track
     * @return true if the operation completed successfully
     * @throws NullPointerException     if name is null
     * @throws IllegalArgumentException if the name is invalid
     * @deprecated in favour of {@link TrackManager#createAndLoadTrack(String)}
     */
    @NonNull
    @Deprecated
    CompletableFuture<Boolean> createAndLoadTrack(@NonNull String name);

    /**
     * Loads a track into the plugins local storage.
     *
     * @param name the name of the track
     * @return true if the operation completed successfully
     * @throws NullPointerException     if name is null
     * @throws IllegalArgumentException if the name is invalid
     * @deprecated in favour of {@link TrackManager#loadTrack(String)}
     */
    @NonNull
    @Deprecated
    CompletableFuture<Boolean> loadTrack(@NonNull String name);

    /**
     * Loads all tracks from the storage into memory
     *
     * @return true if the operation completed successfully.
     * @deprecated in favour of {@link TrackManager#loadAllTracks()}
     */
    @NonNull
    @Deprecated
    CompletableFuture<Boolean> loadAllTracks();

    /**
     * Saves a track back to storage. You should call this after you make any changes to a track.
     *
     * @param track the track to save
     * @return true if the operation completed successfully.
     * @throws NullPointerException  if track is null
     * @throws IllegalStateException if the track instance was not obtained from LuckPerms.
     * @deprecated in favour of {@link TrackManager#saveTrack(Track)}
     */
    @NonNull
    @Deprecated
    CompletableFuture<Boolean> saveTrack(@NonNull Track track);

    /**
     * Permanently deletes a track from storage
     *
     * @param track the track to delete
     * @return true if the operation completed successfully.
     * @throws NullPointerException  if track is null
     * @throws IllegalStateException if the track instance was not obtained from LuckPerms.
     * @deprecated in favour of {@link TrackManager#deleteTrack(Track)}
     */
    @NonNull
    @Deprecated
    CompletableFuture<Boolean> deleteTrack(@NonNull Track track);

    /**
     * Saves UUID caching data to the global cache
     *
     * @param username the users username
     * @param uuid     the users mojang unique id
     * @return true if the operation completed successfully.
     * @throws NullPointerException     if either parameters are null
     * @throws IllegalArgumentException if the username is invalid
     * @deprecated in favour of {@link UserManager#savePlayerData(UUID, String)}
     */
    @NonNull
    CompletableFuture<Boolean> saveUUIDData(@NonNull String username, @NonNull UUID uuid);

    /**
     * Gets a UUID from a username
     *
     * @param username the corresponding username
     * @return a uuid object, could be null
     * @throws NullPointerException     if either parameters are null
     * @throws IllegalArgumentException if the username is invalid
     * @deprecated in favour of {@link UserManager#lookupUuid(String)}
     */
    @NonNull
    CompletableFuture<UUID> getUUID(@NonNull String username);

    /**
     * Gets a username from a UUID
     *
     * @param uuid the corresponding uuid
     * @return a name string, could be null
     * @throws NullPointerException if either parameters are null
     * @since 2.17
     * @deprecated in favour of {@link UserManager#lookupUsername(UUID)}
     */
    @NonNull
    @Deprecated
    CompletableFuture<String> getName(@NonNull UUID uuid);

    /**
     * Returns an executor which will run all passed runnables on the
     * main server thread.
     *
     * <p>This method is deprecated as plugins should create and use their own
     * executor instances.</p>
     *
     * @return an executor instance
     * @deprecated as plugins should create their own executors
     */
    @NonNull
    @Deprecated
    Executor getSyncExecutor();

    /**
     * Returns an executor which will run all passed runnables asynchronously
     * using the platforms scheduler and thread pools.
     *
     * <p>This method is deprecated as plugins should create and use their own
     * executor instances.</p>
     *
     * @return an executor instance
     * @deprecated as plugins should create their own executors
     */
    @NonNull
    @Deprecated
    Executor getAsyncExecutor();

}
