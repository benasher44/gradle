/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.file;


import org.gradle.api.file.FileCollection;

import java.io.File;

public interface FileCollectionInternal extends FileCollection {

    /**
     * Adds a logical description of the potential contents of this collection to the builder.
     * <p>
     * That is, registers a description of the parts of the file system that can influence the actual contents of the collection.
     * <p>
     * It is not required that an absolutely accurate description is added.
     * For example, the description added to the builder may not consider all kinds of filtering that the file collection actually applies.
     *
     * @param builder the receiver of the description.
     */
    void registerWatchPoints(FileSystemSubset.Builder builder);

    /**
     * In a {@link FileCollection} hierarchy visits the leaves of the hierarchy.
     *
     * <p>The implementation of this method should not do any work to calculate the files that make up this collection. The visitor may choose to query each element it receives for its elements, or may not.
     *
     * <p>The implementation should call the most specific method on {@link FileCollectionLeafVisitor} that it is able to.</p>
     */
    void visitLeafCollections(FileCollectionLeafVisitor visitor);

    /**
     * Visits the contents of the collection.
     * Visits the same files as {@link #getFiles()}, but there is no guarantee that each file is visited only once.
     * Stops visiting if visitor returns {@code false}.
     *
     * @return {@code true} if all elements were processed.
     */
    boolean visitContents(CancellableVisitor<File> visitor);

    interface CancellableVisitor<T> {
        /**
         * Visits the given object.
         *
         * @return {@code true} if visiting should continue with the next element,
         * {@code false} if it should stop.
         */
        boolean accept(T file);
    }
}
