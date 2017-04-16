package io.github.todokr;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import io.github.todokr.utils.Logger;

public class HttpProtocol {

    private static Logger logger = new Logger(HttpProtocol.class.getSimpleName());
    private AsynchronousSocketChannel socket;
    private HttpRequestHandler requestHandler = new HttpRequestHandler();

    public HttpProtocol(AsynchronousSocketChannel socket) {
        this.socket = socket;
    }

    public void process() {

        ByteBuffer buff = ByteBuffer.allocateDirect(1024 * 4); // TODO magic
        socket.read(buff, buff, new CompletionHandler<Integer, ByteBuffer>() {


            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                logger.log("read: " + attachment.position());

                if (result < 0) {
                    try {
                        logger.log("result is empty");
                        socket.close();
                    } catch (IOException e) {
                        // do nothing
                    }
                }

                if (buff.hasRem aining()) {
                    logger.log("remain...");
                    socket.read(attachment, attachment, this);
                    return;
                }

                buff.flip(); // 入ったデータ量以上読めなくする（= limitをposition同じ値にセットした上でpositionを0にする）
                try {
                    HttpRequest request = new HttpRequest(buff); // TODO Arrays.toStringしてしまってもいいかもしれない
                    logger.log("create request");
                    HttpResponse response = requestHandler.handleRequest(request);
                    logger.log("create response");
                    response.writeTo(socket);
                } catch (IOException e) {
                    // TODO 400や500のレスポンスを返すなどする。各種の例外を自分で作るべき。
                }
            }

            @Override
            public void failed(Throwable e, ByteBuffer attachment) {
                logger.error("Failed to process: " + e.getMessage());
            }
        });
    }
}
