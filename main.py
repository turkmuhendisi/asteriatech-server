import cv2
import numpy as np

def activate_normal_camera_and_detect_objects():
    # Tam dosya yollarını ayarlayın
    weights_path = r"C:\Users\SE Mustafa\Pyhton Proje\pythonProject2\yolov3.weights"
    config_path = r"C:\Users\SE Mustafa\Pyhton Proje\pythonProject2\yolov3.cfg"
    names_path = r"C:\Users\SE Mustafa\Pyhton Proje\pythonProject2\coco.names"

    # YOLO modeli yükle
    try:
        net = cv2.dnn.readNet(weights_path, config_path)
    except cv2.error as e:
        print(f"Error loading network: {e}")
        return

    # Class isimlerini yükle
    with open(names_path, 'r') as f:
        classes = f.read().strip().split('\n')

    # Kamera başlat
    cap = cv2.VideoCapture(0)

    while True:
        # Kareyi yakala
        ret, frame = cap.read()
        if not ret:
            break

        # Nesne tespiti için YOLO'yu kullan
        blob = cv2.dnn.blobFromImage(frame, 0.00392, (416, 416), (0, 0, 0), True, crop=False)
        net.setInput(blob)
        outs = net.forward(get_output_layers(net))

        # Algılanan nesneleri ve sınır kutularını çiz
        class_ids, confidences, boxes = [], [], []
        for out in outs:
            for detection in out:
                scores = detection[5:]
                class_id = np.argmax(scores)
                confidence = scores[class_id]
                if confidence > 0.5:
                    center_x = int(detection[0] * frame.shape[1])
                    center_y = int(detection[1] * frame.shape[0])
                    w = int(detection[2] * frame.shape[1])
                    h = int(detection[3] * frame.shape[0])
                    x = center_x - w // 2
                    y = center_y - h // 2
                    class_ids.append(class_id)
                    confidences.append(float(confidence))
                    boxes.append([x, y, w, h])

        indices = cv2.dnn.NMSBoxes(boxes, confidences, 0.5, 0.4)

        for i in indices:
            i = i[0]
            box = boxes[i]
            x, y, w, h = box[0], box[1], box[2], box[3]
            draw_prediction(frame, class_ids[i], confidences[i], x, y, x + w, y + h, classes)

        # Sonuçları göster
        cv2.imshow("Object Detection", frame)

        # 'q' tuşuna basarak çık
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    # Kamera ve tüm pencereleri serbest bırak
    cap.release()
    cv2.destroyAllWindows()

def get_output_layers(net):
    layer_names = net.getLayerNames()
    output_layers = [layer_names[i[0] - 1] for i in net.getUnconnectedOutLayers()]
    return output_layers

def draw_prediction(img, class_id, confidence, x, y, x_plus_w, y_plus_h, classes):
    label = f"{classes[class_id]}: {confidence:.2f}"
    color = (0, 255, 0)
    cv2.rectangle(img, (x, y), (x_plus_w, y_plus_h), color, 2)
    cv2.putText(img, label, (x - 10, y - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.5, color, 2)

# Fonksiyonu çağırın
activate_normal_camera_and_detect_objects()
