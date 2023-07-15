package org.prography.kagongsillok.common.logging;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.jetbrains.annotations.NotNull;

@Plugin(name = "Slack", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class SlackAppender extends AbstractAppender {

    private final String channel;
    private final String token;

    public SlackAppender(final String name, final Filter filter,
            final Layout<? extends Serializable> layout, final boolean ignoreExceptions,
            final Property[] properties,
            final String channel,
            final String token) {
        super(name, filter, layout, ignoreExceptions, properties);
        this.channel = channel;
        this.token = token;
    }

    @PluginFactory
    public static SlackAppender createAppender(
            @PluginAttribute("name") final String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter,
            @PluginAttribute("channel") @Required(message = "No channel provided") final String channel,
            @PluginAttribute("token") @Required(message = "No channel provided") final String token
    ) {
        if (name == null) {
            LOGGER.error("Error! Name is Null");
            return null;
        }

        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new SlackAppender(name, filter, layout, true, Property.EMPTY_ARRAY, channel, token);
    }

    @Override
    public void append(final LogEvent event) {
        if (event == null || event.getMessage() == null) {
            return;
        }
        final String level = event.getLevel().toString();
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String timestamp = dateFormat.format(event.getTimeMillis());
        final String loggerName = event.getLoggerName();
        final String msg = event.getMessage().getFormattedMessage();
        final Throwable throwable = event.getThrown();

        final String message = toLogMessage(level, timestamp, loggerName, msg, throwable);
        sendSlackMessage(message);
    }

    @NotNull
    private String toLogMessage(
            final String level,
            final String timestamp,
            final String loggerName,
            final String msg,
            final Throwable throwable
    ) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(timestamp);
        stringBuilder.append("] [");
        stringBuilder.append(level);
        stringBuilder.append("] [");
        stringBuilder.append(loggerName);
        stringBuilder.append("] - ");
        stringBuilder.append(msg);
        if (throwable != null) {
            final StringWriter sw = new StringWriter();
            final PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            stringBuilder.append(sw);
        }
        return stringBuilder.toString();
    }

    public void sendSlackMessage(final String message){
        try{
            final MethodsClient methods = Slack.getInstance()
                    .methods(token);

            final ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(channel)
                    .text(message)
                    .build();

            methods.chatPostMessage(request);
        } catch (SlackApiException | IOException ignore) {
            ignore.printStackTrace();
        }
    }
}
