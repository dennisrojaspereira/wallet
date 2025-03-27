package kafka

import (
	"github.com/segmentio/kafka-go"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/mock"
	"testing"
)

type MockReader struct {
	mock.Mock
}

func (m *MockReader) ReadMessage() (kafka.Message, error) {
	args := m.Called()
	return args.Get(0).(kafka.Message), args.Error(1)
}

func TestConsumer_Consume(t *testing.T) {

	mockReader := new(MockReader)
	mockReader.On("ReadMessage").Return(kafka.Message{Value: []byte("Test Message")}, nil)

	consumer := &Consumer{
		Reader: mockReader,
	}

	msg, err := consumer.Consume()

	assert.NoError(t, err)
	assert.Equal(t, "Test Message", string(msg.Value))

	mockReader.AssertExpectations(t)
}
