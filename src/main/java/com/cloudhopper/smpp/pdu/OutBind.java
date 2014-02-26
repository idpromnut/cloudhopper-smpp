package com.cloudhopper.smpp.pdu;

/*
 * #%L
 * ch-smpp
 * %%
 * Copyright (C) 2009 - 2012 Cloudhopper by Twitter
 * %%
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
 * #L%
 */

import com.cloudhopper.commons.util.StringUtil;
import com.cloudhopper.smpp.SmppConstants;
import com.cloudhopper.smpp.type.RecoverablePduException;
import com.cloudhopper.smpp.type.UnrecoverablePduException;
import com.cloudhopper.smpp.util.ChannelBufferUtil;
import com.cloudhopper.smpp.util.PduUtil;
import org.jboss.netty.buffer.ChannelBuffer;

/**
 * 
 * @author joelauer (twitter: @jjlauer or <a href="http://twitter.com/jjlauer" target=window>http://twitter.com/jjlauer</a>)
 */
public class OutBind extends Pdu {

    private String systemId;
    private String password;

    public OutBind() {
        super(SmppConstants.CMD_ID_OUTBIND, "outbind", true);
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(final String iSystemId) {
        systemId = iSystemId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String iPassword) {
        password = iPassword;
    }

    @Override
    protected int calculateByteSizeOfBody() {
        int bodyLength = 0;
        bodyLength += PduUtil.calculateByteSizeOfNullTerminatedString(this.systemId);
        bodyLength += PduUtil.calculateByteSizeOfNullTerminatedString(this.password);
        return bodyLength;
    }

    @Override
    public void readBody(final ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        this.systemId = ChannelBufferUtil.readNullTerminatedString(buffer);
        this.password = ChannelBufferUtil.readNullTerminatedString(buffer);
    }

    @Override
    public void writeBody(final ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        ChannelBufferUtil.writeNullTerminatedString(buffer, this.systemId);
        ChannelBufferUtil.writeNullTerminatedString(buffer, this.password);
    }

    @Override
    protected void appendBodyToString(final StringBuilder buffer) {
        buffer.append("systemId [");
        buffer.append(StringUtil.toStringWithNullAsEmpty(this.systemId));
        buffer.append("] password [");
        buffer.append(StringUtil.toStringWithNullAsEmpty(this.password));
        buffer.append("]");
    }
}