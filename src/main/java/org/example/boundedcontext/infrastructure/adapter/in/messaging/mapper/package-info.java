/**
 * Mappers from inbound integration events to application commands.
 *
 * <p>Translate external vocabulary into the local ubiquitous language before invoking a handler.
 *
 * <p>Naming convention: {@code <Event>CommandMapper}. Example: {@code OrderShippedCommandMapper}.
 */
package org.example.boundedcontext.infrastructure.adapter.in.messaging.mapper;
